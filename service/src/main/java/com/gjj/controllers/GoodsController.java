package com.gjj.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gjj.enums.ErrorCode;
import com.gjj.enums.ErrorMessage;
import com.gjj.exceptions.BusinessException;
import com.gjj.exceptions.UnAuthorizedException;
import com.gjj.models.Attachment;
import com.gjj.models.Goods;
import com.gjj.models.Subscribe;
import com.gjj.models.User;
import com.gjj.services.AttachmentService;
import com.gjj.services.AuthenticationUserService;
import com.gjj.services.GoodsService;
import com.gjj.services.SubscribeService;
import com.gjj.utils.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by gjj on 2018-03-06.
 */
@RestController
public class GoodsController {

    @Value("${spring.img.location}")
    private String location;

    @Value("${spring.img.url}")
    private String imagesUrl;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private AuthenticationUserService authenticationUserService;

    @Autowired
    private SubscribeService subscribeService;



    @ResponseBody
    @GetMapping("/goods/{id}")
    public ResponseEntity<?> getGoodsById(@PathVariable Integer id) {

        Goods goods = goodsService.getGoodsById(id);
        return ResponseEntity.ok(goods);
    }


    @ResponseBody
    @GetMapping("/goods")
    public ResponseEntity<?> getGoods(@RequestParam(required = false, value = "id") Integer id,
                                      @RequestParam(required = false, value = "goodsName") String goodsName,
                                      @RequestParam(required = false, value = "type") String type,
                                      @RequestParam(required = false, value = "customerId") Integer customerId,
                                      @RequestParam(value = "${spring.data.rest.page-param-name}", required = false, defaultValue = "${spring.data.rest.default-page-number}") Integer pageNum,
                                      @RequestParam(value = "${spring.data.rest.limit-param-name}", required = false, defaultValue = "${spring.data.rest.default-page-size}") Integer pageSize,
                                      @RequestParam(value = "${spring.data.rest.sort-param-name}", required = false, defaultValue = "id,desc") String sort) {
        Sort sort1 = new Sort(Sort.Direction.DESC, "bulletinDate");
        Pageable pageable = new PageRequest(pageNum, pageSize, sort1);
//        Page<Goods> goods = goodsService.getGoods(id, pageable);
        return ResponseEntity.ok(goodsService.getGoods(id, goodsName, type, customerId, pageable));
    }

    @ResponseBody
    @PostMapping("/goods/publish/{uid}")
    @Transactional
    public ResponseEntity<?> publishGoodsInfo(@PathVariable Integer uid, @RequestBody JsonNode jsonNode) {
        Goods goods;
        User user = authenticationUserService.getUser(uid);
        try {
            goods = new ObjectMapper().readValue(jsonNode.traverse(), Goods.class);
            goods.setBulletinDate(new Date());
            goods.setUser(user);
//            newGoods = goodsService.saveGoods(goods);

        } catch (IOException e) {
            throw new UnAuthorizedException(ErrorCode.JSON_TO_OBJECT_ERROR, ErrorMessage.ERROR_CHANGE_TYPE);
        }
        return ResponseEntity.ok(goodsService.saveGoods(goods).getId());

    }

    @ResponseBody
    @PostMapping("/goods/images/upload")
    @Transactional
    public ResponseEntity<?> publishGoodsImages(HttpServletRequest request, @RequestParam(name = "file", required = false) MultipartFile multipartFiles) throws Exception {
        Goods newGoods ;
        String goodsId = request.getParameter("goods");
        try {
            Integer id =  Integer.valueOf(goodsId.trim());
//            newGoods = goodsService.getGoodsById(Integer.valueOf(goodsId.trim()));
            if (multipartFiles != null) {
                List urlList = uploadFile(multipartFiles, id);
            }

        } catch (IOException e) {
            throw new UnAuthorizedException(ErrorCode.JSON_TO_OBJECT_ERROR, ErrorMessage.ERROR_CHANGE_TYPE);
        }
        return ResponseEntity.ok(null);
    }

    @ResponseBody
//    @PostMapping("/goods/upload/images")
    public List uploadFile(MultipartFile multipartFiles, Integer goodsId) throws Exception {
//        List urlList = new ArrayList();
        String url = null;
        try {
//            for (int i = 0; i < multipartFiles.length; i++) {
                MultipartFile multipartFile = multipartFiles;
                if (multipartFile.isEmpty()) {
                    throw new BusinessException(ErrorCode.IMG_NOT_EMPTY, ErrorMessage.IMG_NOT_EMPTY);
                }
                String contentType = multipartFile.getContentType();
                if (!contentType.contains("")) {
                    throw new BusinessException(ErrorCode.IMG_FORMAT_ERROR, ErrorMessage.IMG_FORMAT_ERROR);
                }
                String root_fileName = multipartFile.getOriginalFilename();
//            logger.info("上传图片:name={},type={}", root_fileName, contentType);
//            User currentUser = userService.getCurrentUser();   //获取路径
//            User currentUser = userService.getCurrentUser();   //获取路径
//            String return_path = ImageUtil.getFilePath(currentUser);
                String filePath = location;
                String file_name = null;
                Attachment attachment = new Attachment();
                file_name = ImageUtil.saveImg(multipartFile, filePath);
                if (!"".equals(file_name)) {
                    url = imagesUrl + file_name;
//                    urlList.add(url);
                    attachmentService.saveAttachmentByParams(file_name, url, goodsId);
//                    attachment.setAttachmentName(file_name);
//                    attachment.setAttachmentUrl(url);
//                    attachment.setGoods(goods);
//                    attachmentService.saveAttachment(attachment);
                }
//            }
        } catch (IOException e) {
//            goodsService.deleteGoods(goods);
            throw new BusinessException(ErrorCode.SAVE_IMG_ERROE, ErrorMessage.SAVE_IMG_ERROE);
        }
        return null;
    }

    @ResponseBody
    @PostMapping("/goods/goodsDeal")
    public ResponseEntity<?> goodsDeal(@RequestBody JsonNode jsonNode) {
        Map map = new HashMap<>();
        try {
            map = new ObjectMapper().readValue(jsonNode.traverse(), Map.class);
        } catch (IOException e) {
            throw new UnAuthorizedException(ErrorCode.JSON_TO_OBJECT_ERROR, ErrorMessage.ERROR_CHANGE_TYPE);
        }
        Integer goodsId = Integer.valueOf(map.get("goodsId").toString().trim());
        Integer customerId = Integer.valueOf(map.get("customerId").toString().trim());
        Goods goods = goodsService.getGoodsById(goodsId);
        goods.setCustomerId(customerId);
        goodsService.saveGoods(goods);
        return ResponseEntity.ok(null);
    }

    @ResponseBody
    @PostMapping("/goods/delete/{id}")
    public ResponseEntity<?> deleteGoods(@PathVariable Integer id) {
        goodsService.deleteGoods(id);
        return ResponseEntity.ok(null);
    }

    @ResponseBody
    @GetMapping("/goods/getFollow")
    public ResponseEntity<?> getFollowGoods(@RequestParam(required = false, value = "id") Integer id,
                                            @RequestParam(required = false, value = "goodsName") String goodsName,
                                            @RequestParam(required = false, value = "type") String type) {
        List list = new ArrayList();
        List<Subscribe> subscribeList = subscribeService.getSubscribe(id, "");
        for (Subscribe subscribe : subscribeList) {
            List<Goods> goodsList = goodsService.getAllGoodsByUser(subscribe.getId(),goodsName,type);
            list.addAll(goodsList);
        }
        return ResponseEntity.ok(list);

    }


}
