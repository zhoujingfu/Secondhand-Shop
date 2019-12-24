import axios from 'axios';

let base = '/api/main'

export const GetUser = params => {
  return axios.post(base + '/getUser', params);
}
