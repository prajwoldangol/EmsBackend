import { createAsyncThunk, createSlice } from '@reduxjs/toolkit'
import axiosBase from '../axiosBase'
import { ScheduleData } from '@/data/userData'

// import axios from 'axios'
// import { EmptyTask, GetTask } from './taskSlice'
export const AddSchedule = createAsyncThunk(
  'schedule/AddSchedule',
  async (scheduleData: ScheduleData, thunkAPI) => {
    const response = await axiosBase.post('schedule/timetable', scheduleData)
    const data = response.data
    // if (data.statusCode === 200 && data.employerId && data.token) {
    //   //   await thunkAPI
    //   //     .dispatch(GetEmployer([data.employerId, data.token]))
    //   //     .unwrap() // Unwrap to properly handle any errors
    // }
    // console.log('inner' + data)
    const schedules = await thunkAPI.dispatch(
      GetScheduleData(scheduleData.employerId)
    )
    console.log('Schedules' + schedules)
    return data
  }
)
export const GetScheduleData = createAsyncThunk(
  'schedule/GetScheduleData',
  async (employerId: string) => {
    const response = await axiosBase.get(
      `schedule/timetable/future/${employerId}`
    )
    const data = response.data
    return data
  }
)

export interface userState {
  scheduleData: [Record<string, any>]
}
const initState: userState = {
  scheduleData: [{}],
}
const ScheduleSlice = createSlice({
  name: 'schedule',

  initialState: initState,
  reducers: {},
  extraReducers(builder) {
    builder
      .addCase(AddSchedule.pending, (state, action) => {})
      .addCase(AddSchedule.fulfilled, (state, action) => {})
      .addCase(AddSchedule.rejected, (state, action: any) => {})
      .addCase(GetScheduleData.fulfilled, (state, action) => {
        state.scheduleData = action.payload
      })
  },
})
// export const {  } = ScheduleSlice.actions
export default ScheduleSlice
