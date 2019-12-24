import axios from 'axios';

let base = '/api/notice'

export const Save = params => {
    return axios.post(base + '/save', params);
}

export const GetAll = params => {
    return axios.get(base + '/findAll', params);
}

export const Remove = params => {
    return axios.get(base + '/remove/' + params);
}