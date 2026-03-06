import axios from 'axios';

const API = axios.create({
    baseURL : "http://localhost:8083/analytics"
})

export const getRequests = () => API.get("/requests")
export const getBlockedKeys = () => API.get("/blocked-keys")