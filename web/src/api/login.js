import axios from 'axios';

let base = '/api/auth'

export const Login = params => {
  return axios.post(base + '/login', params);
};