import axios from 'axios';

const API = 'http://localhost:8080/api/v1';

export const fetchProducts = async ({ page, filter }: { page: number; filter: string }) => {
  try {
    const res = await axios.get('http://localhost:8080/api/v1/products', {
      params: { page, category: filter },
    });
    return res.data;
  } catch (err) {
    return err;
  }
};

export const uploadFile = async (file: File, username: string, password: string) => {
  const formData = new FormData();
  formData.append('file', file);
  try {
    const res = await axios.post('http://localhost:8080/api/v1/products/upload', formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
        Authorization: "Basic " + btoa(`${username}:${password}`),
      },
    });
    return res.data;
  } catch (error) {
    return error;
  }
};

export const downloadFile = async (filter: string) => {
  try {
    const res = await axios.get('http://localhost:8080/api/v1/products/download', {
      params: { category: filter },
      responseType: 'blob'
    });
    const url = window.URL.createObjectURL(new Blob([res.data]));
    const link = document.createElement('a');
    link.href = url;
    link.setAttribute('download', 'products.xlsx');
    document.body.appendChild(link);
    link.click();
    return res;
  } catch (error) {
    return error;
  }
};

export const register = async ({ username, password, role }: { username: string, password: string, role: string }) => {
  try {
    const res = await axios.post(`${API}/users/register`, {
      username,
      password,
      role
    });
    return res;
  } catch (err: any) {
    return err;
  }
};

export const login = async ({ username, password }: { username: string, password: string }) => {
  try {
    const res = await axios.post(`${API}/users/login`, {
      username,
      password
    });
    return res.data;
  } catch (err) {
    return err;
  }
};
