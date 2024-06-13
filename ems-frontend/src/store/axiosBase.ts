import axios from 'axios'
const axiosBase = axios.create({
  baseURL: 'http://localhost:9090',
  headers: {
    'Content-Type': 'application/json',
  },
})

export default axiosBase
