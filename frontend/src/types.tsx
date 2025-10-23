export interface User {
  username: string;
  role: 'ADMIN' | 'VIEWER';
}

export interface Product {
  id: number;
  name: string;
  category: string;
  price: number;
  quantity: number;
}