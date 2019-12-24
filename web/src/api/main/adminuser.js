import axios from 'axios';

let base = '/api'

export const Save = params => {
    return axios.post(base + '/admin/personal/management/' + params + '/state/' + 0);
}

export const GetAll = params => {
    return axios.get(base + '/users', params);
}

export const Remove = params=> {
    return axios.post(base + '/admin/personal/management/' + params + '/state/' + 1);
}