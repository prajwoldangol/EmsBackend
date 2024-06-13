import { combineReducers, configureStore } from '@reduxjs/toolkit'

import storage from 'redux-persist/lib/storage'
import { persistReducer, persistStore } from 'redux-persist'
// import UserSlice from './slice/userSlice'
// import TaskSlice from './slice/taskSlice'
import UserSlice from './slice/userSlice'
import EmployeeSlice from './slice/employeeSlice'
import ScheduleSlice from './slice/scheduleSlice'
const persistConfig = {
  key: 'root',
  version: 1,
  storage,
}

const reducer = combineReducers({
  users: UserSlice.reducer,
  employee: EmployeeSlice.reducer,
  schedule: ScheduleSlice.reducer,
  // tasks: TaskSlice.reducer,
  //   todos: TodoSlice.reducer,
})

const persistedReducer = persistReducer(persistConfig, reducer)
const store = configureStore({
  reducer: persistedReducer,
})

export const persistor = persistStore(store)
// Define RootState and AppDispatch types
export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch

export default store
