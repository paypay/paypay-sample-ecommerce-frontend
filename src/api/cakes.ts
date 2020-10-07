import axios from 'axios';
import {BASE_URL} from '@/constants/url'
export const getCakes = ()=>{
    return axios.get(`${BASE_URL}/cakes`)
}

export const makePayment = (payload:any)=>{
    return axios.post(`${BASE_URL}/create-qr`,payload,{
        headers:{
            'Content-Type': 'application/json'
        }
    })
}


export const getOrderStatus = (id:string)=>{
    return axios.get(`${BASE_URL}/order-status/${id}`)
}
