export interface iItem {
    title: string,
    id: number,
    price: number,
    image: string
}

export interface iAddToCartPayload {
    id: number
}

export interface iUnitPrice {
    amount:number,
    currency:string
}
export interface iOrderItem {
    unitPrice:iUnitPrice,
    productId:string,
    quantity:number,
    category:string,
    name:string,
}         