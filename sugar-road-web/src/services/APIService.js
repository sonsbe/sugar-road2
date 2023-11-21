import { API_BASE_URL } from '../configs/APIConfig';
import axios from 'axios';

const apiService = axios.create({
    baseURL: API_BASE_URL, 
    headers: {
        // Host : window.location.hostname,
        Accept: "application/json"
    }
});

const authApiService = axios.create({
    baseURL: API_BASE_URL, 
    headers: {
        // Host : window.location.hostname
        // Authorization : token .... 등 인증 관련
    }
});

export {
    apiService,
    authApiService
}