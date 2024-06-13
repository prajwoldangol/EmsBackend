import { createAsyncThunk, createSlice } from '@reduxjs/toolkit'
import axiosBase from '../axiosBase'
import {
  DepartmentDto,
  EmployeeSignupDto,
  EmployerSignupDto,
  PasswordResetDto,
  StripePayDto,
  UserData,
  userDataDto,
} from '@/data/userData'
import { LogoutActionEmployee, LogoutActionEmployeeData } from './employeeSlice'
// import axios from 'axios'
// import { EmptyTask, GetTask } from './taskSlice'
export const UserLogin = createAsyncThunk(
  'employer/UserLogin',
  async (credentials: string[], thunkAPI) => {
    const response = await axiosBase.post('user/employer/login', {
      username: credentials[0],
      password: credentials[1],
    })
    const data = response.data
    if (data.statusCode === 200 && data.employerId && data.token) {
      await thunkAPI
        .dispatch(GetEmployer([data.employerId, data.token]))
        .unwrap() // Unwrap to properly handle any errors
    }
    // console.log(data)
    return data
  }
)
export const EmployerRegister = createAsyncThunk(
  'user/EmployerRegister',
  async (userdata: EmployerSignupDto) => {
    const data = await axiosBase.post('user/employer/register', userdata)
    return data.data
  }
)
export const AddDepartment = createAsyncThunk(
  'user/AddDepartment',
  async (userdata: DepartmentDto, thunkAPI) => {
    const data = await axiosBase.post('user/employer/departments', userdata, {
      headers: {
        Authorization: `Bearer ${userdata.token}`,
      },
    })
    thunkAPI.dispatch(GetEmployer([userdata.employerId, userdata.token]))
    return data.data
  }
)
export const EditDepartment = createAsyncThunk(
  'user/EditDepartment',
  async (userdata: DepartmentDto, thunkAPI) => {
    const data = await axiosBase.put(
      `user/employer/departments/${userdata.id}`,
      { name: userdata.name },
      {
        headers: {
          Authorization: `Bearer ${userdata.token}`,
        },
      }
    )
    thunkAPI.dispatch(GetEmployer([userdata.employerId, userdata.token]))
    return data.data
  }
)
export const DeleteDepartment = createAsyncThunk(
  'user/DeleteDepartment',
  async (userdata: DepartmentDto, thunkAPI) => {
    const data = await axiosBase.delete(
      `user/employer/departments/${userdata.id}`,
      {
        headers: {
          Authorization: `Bearer ${userdata.token}`,
        },
      }
    )
    thunkAPI.dispatch(GetEmployer([userdata.employerId, userdata.token]))
    return data.data
  }
)
export const AddEmployee = createAsyncThunk(
  'user/AddEmployee',
  async (userdata: EmployeeSignupDto, thunkAPI) => {
    const data = await axiosBase.post('user/employer/newEmployee', userdata, {
      headers: {
        Authorization: `Bearer ${userdata.token}`,
      },
    })
    thunkAPI.dispatch(GetEmployer([userdata.employerId, userdata.token]))
    return data.data
  }
)
export const UpdateEmployeePassword = createAsyncThunk(
  'user/UpdateEmployeePassword',
  async (userdata: PasswordResetDto) => {
    const data = await axiosBase.put(
      `user/employee/password/${userdata.empId}`,
      userdata.newPassword
    )
    return data.data
  }
)
export const GetEmployer = createAsyncThunk(
  'user/GetEmployer',
  async (empData: string[]) => {
    const data = await axiosBase.get(`user/employer/${empData[0]}`, {
      headers: {
        Authorization: `Bearer ${empData[1]}`,
      },
    })
    return data.data
  }
)
export const StripePay = createAsyncThunk(
  'user/StripePay',
  async (userdata: StripePayDto) => {
    const data = await axiosBase.post('user/employer/pay', userdata)
    // const result = await thunkAPI.dispatch()
    return data.data
  }
)
export const LogoutUser = createAsyncThunk(
  'user/LogoutUser',
  async (_, thunkAPI) => {
    thunkAPI.dispatch(LogoutActionEmployeeData())
  }
)
export interface userState {
  token: string
  loggedIn: boolean
  loggedInUser: string
  userId: string
  role: string
  error: string[]
  success: string
  loading: boolean
  userData: Record<string, any>
}
const initState: userState = {
  token: '',
  loggedIn: false,
  loggedInUser: '',
  userId: '',
  role: 'USER',
  error: [],
  success: '',
  loading: false,
  userData: {},
}
const UserSlice = createSlice({
  name: 'user',

  initialState: initState,
  reducers: {
    LoginActions(state, action) {
      state.token = action.payload.token
      state.loggedInUser = action.payload.username
      state.userId = action.payload.employerId
      state.loggedIn = true
    },
    LogoutActions() {
      return initState
    },
  },
  extraReducers(builder) {
    builder
      .addCase(UserLogin.pending, (state, action) => {
        state.loading = true
      })
      .addCase(UserLogin.fulfilled, (state, action) => {
        state.token = action.payload.token
        state.loggedInUser = action.payload.username
        state.userId = action.payload.employerId
        state.loggedIn = action.payload.statusCode === 200 ? true : false
        state.role = action.payload.role
        state.loading = false
      })
      .addCase(UserLogin.rejected, (state, action: any) => {
        // state.error = action.payload.error ? action.payload.message : []
        state.loading = false
      })
      .addCase(EmployerRegister.pending, (state) => {
        state.loading = true
      })
      .addCase(EmployerRegister.fulfilled, (state, action) => {
        state.error =
          action.payload.error === '500'
            ? ['Duplicate Account Detected. Please Use Unique Email And Phone!']
            : []
        state.success = !action.payload.error
          ? 'Registered user Successfully !!'
          : ''
        state.loading = false
      })
      .addCase(EmployerRegister.rejected, (state) => {
        // state.error = action.payload.error ? action.payload.message : []
        state.loading = false
      })
      .addCase(StripePay.pending, (state) => {
        state.loading = true
      })
      .addCase(StripePay.fulfilled, (state) => {
        state.loading = false
      })
      .addCase(GetEmployer.fulfilled, (state, action) => {
        state.userData = action.payload
      })
      .addCase(LogoutUser.fulfilled, () => {
        return initState
      })
  },
})
export const { LoginActions, LogoutActions } = UserSlice.actions
export default UserSlice
