import axios from 'axios';

let base = '/api'

export const Save = params => {
  return axios.post(base + '/contract/save', params);
}

export const GetAll = params => {
  return axios.get(base + '/goods', params);
}

export const Remove = params => {
  return axios.post(base + '/goods/delete/' + params);
}