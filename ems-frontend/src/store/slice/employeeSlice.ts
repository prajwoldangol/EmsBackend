import { createAsyncThunk, createSlice } from '@reduxjs/toolkit'
import axiosBase from '../axiosBase'

// import axios from 'axios'
// import { EmptyTask, GetTask } from './taskSlice'
export const EmployeeLogin = createAsyncThunk(
  'employee/EmployeeLogin',
  async (credentials: string[], thunkAPI) => {
    const response = await axiosBase.post('user/employee/login', {
      username: credentials[0],
      password: credentials[1],
    })
    const data = response.data
    if (data.statusCode === 200 && data.employerId && data.token) {
      await thunkAPI
        .dispatch(GetEmployee([data.employerId, data.token]))
        .unwrap() // Unwrap to properly handle any errors
    }
    console.log(data)
    return data
  }
)
export const GetEmployee = createAsyncThunk(
  'employee/GetEmployee',
  async (empData: string[]) => {
    const data = await axiosBase.get(`user/employee/${empData[0]}`, {
      headers: {
        Authorization: `Bearer ${empData[1]}`,
      },
    })
    return data.data
  }
)
export const LogoutActionEmployeeData = createAsyncThunk(
  'employee/LogoutActionEmployeeData',
  async (_, thunkAPI) => {
    thunkAPI.dispatch(LogoutActionEmployee())
  }
)
export interface employeeState {
  token: string
  loggedIn: boolean
  loggedInUser: string
  userId: string
  role: string
  success: string
  loading: boolean
  userData: Record<string, any>
}
const initState: employeeState = {
  token: '',
  loggedIn: false,
  loggedInUser: '',
  userId: '',
  role: 'EMPLOYEE',
  success: '',
  loading: false,
  userData: {},
}
const EmployeeSlice = createSlice({
  name: 'employee',

  initialState: initState,
  reducers: {
    LogoutActionEmployee(state) {
      return initState
    },
  },
  extraReducers(builder) {
    builder
      .addCase(EmployeeLogin.pending, (state, action) => {
        state.loading = true
      })
      .addCase(EmployeeLogin.fulfilled, (state, action) => {
        state.token = action.payload.token
        state.loggedInUser = action.payload.username
        state.userId = action.payload.employerId
        state.loggedIn = action.payload.statusCode === 200 ? true : false
        state.role = action.payload.role
        state.loading = false
      })
      .addCase(EmployeeLogin.rejected, (state, action: any) => {
        state.loading = false
      })
      .addCase(LogoutActionEmployeeData.fulfilled, () => {
        return initState
      })
  },
})
export const { LogoutActionEmployee } = EmployeeSlice.actions
export default EmployeeSlice
