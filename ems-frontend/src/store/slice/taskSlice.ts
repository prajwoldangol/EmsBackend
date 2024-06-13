import { createAsyncThunk, createSlice } from '@reduxjs/toolkit'
import axiosBase from '../../services/axiosBase'
import axios from 'axios'
import {
  CompleteTaskData,
  EditTaskData,
  EditTodoData,
  TaskData,
  TodoData,
} from '../../services/taskTypes'

/* Task Title functions starts here */
export const CreateTask = createAsyncThunk(
  'task/CreateTask',
  async (input: TaskData, thunkAPI) => {
    const state: any = thunkAPI.getState()
    const headers = {
      Authorization: 'Bearer ' + state.users.token,
    }
    const data = await axiosBase.post('api/v1/todo/addTodo', input, {
      headers,
    })
    console.log(data.data)
    return data.data
  }
)
export const EditTask = createAsyncThunk(
  'task/EditTask',
  async (input: EditTaskData, thunkAPI) => {
    const state: any = thunkAPI.getState()
    const headers = {
      Authorization: 'Bearer ' + state.users.token,
    }
    const data = await axiosBase.patch(
      `api/v1/todo/updateTodo/${input.taskId}`,
      input,
      { headers }
    )

    return data.data
  }
)
export const CompleteTask = createAsyncThunk(
  'task/CompleteTask',
  async (input: CompleteTaskData, thunkAPI) => {
    const state: any = thunkAPI.getState()
    const headers = {
      Authorization: 'Bearer ' + state.users.token,
    }
    const data = await axiosBase.patch(
      `api/v1/todo/updateTodo/${input.taskId}`,
      input,
      { headers }
    )
    // console.log(data);
    return data.data
  }
)
export const DeleteTask = createAsyncThunk(
  'task/DeleteTask',
  async (taskId: string, thunkAPI) => {
    const state: any = thunkAPI.getState()
    const headers = {
      Authorization: 'Bearer ' + state.users.token,
    }
    const data = await axiosBase.delete(`api/v1/todo/deleteTodo/${taskId}`, {
      headers,
    })
    return data.data
  }
)
/* end task title functions */
/* Task slice functions starts here */
export const AddTodoToTask = createAsyncThunk(
  'task/AddTodoToTask',
  async (input: TodoData, thunkAPI) => {
    const state: any = thunkAPI.getState()
    const headers = {
      Authorization: 'Bearer ' + state.users.token,
    }
    const data = await axiosBase.post('api/v1/todo/addTodo/task', input, {
      headers,
    })

    return data.data
  }
)
export const CompleteTodoTask = createAsyncThunk(
  'task/CompleteTodoTask',
  async (input: CompleteTaskData, thunkAPI) => {
    const state: any = thunkAPI.getState()
    const headers = {
      Authorization: 'Bearer ' + state.users.token,
    }
    const data = await axiosBase.patch(
      `api/v1/todo/updateTodo/task/${input.taskId}`,
      input,
      { headers }
    )
    // console.log(data);
    return data.data
  }
)
export const UpdateTodoTask = createAsyncThunk(
  'task/UpdateTodoTask',
  async (input: EditTodoData, thunkAPI) => {
    const state: any = thunkAPI.getState()
    const headers = {
      Authorization: 'Bearer ' + state.users.token,
    }
    const data = await axiosBase.patch(
      `api/v1/todo/updateTodo/task/${input.taskId}`,
      input,
      { headers }
    )
    // console.log(data);
    return data.data
  }
)
export const DeleteTodoTask = createAsyncThunk(
  'task/DeleteTodoTask',
  async (taskId: string, thunkAPI) => {
    const state: any = thunkAPI.getState()
    const headers = {
      Authorization: 'Bearer ' + state.users.token,
    }
    const data = await axiosBase.delete(
      `api/v1/todo/deleteTodo/task/${taskId}`,
      { headers }
    )

    return data.data
  }
)
/* end Task slice functions */

// get all todos
export const GetTask = createAsyncThunk(
  'task/GetTask',
  async (userId: string) => {
    const data = await axiosBase.get(`api/v1/todo/getTodoUser/${userId}`)

    return data.data
  }
)
export const LogoutUser = createAsyncThunk(
  'user/Logout',
  async (_, thunkAPI) => {
    thunkAPI.dispatch()
  }
)
interface TaskType {
  userTasks: string[]
  loading: boolean
}
const emptyState: TaskType = {
  userTasks: [],
  loading: false,
}
const TaskSlice = createSlice({
  name: 'task',
  initialState: emptyState,
  reducers: {
    EmptyTask(state) {
      return emptyState
    },
  },
  extraReducers(builder) {
    builder
      .addCase(CreateTask.pending, (state) => {
        state.loading = true
      })
      .addCase(CreateTask.fulfilled, (state, action) => {
        state.loading = false
      })
      .addCase(CreateTask.rejected, (state) => {
        state.loading = false
      })
      .addCase(EditTask.pending, (state) => {
        state.loading = true
      })
      .addCase(EditTask.fulfilled, (state, action) => {
        state.loading = false
      })
      .addCase(EditTask.rejected, (state) => {
        state.loading = false
      })
      .addCase(CompleteTask.pending, (state) => {
        state.loading = true
      })
      .addCase(CompleteTask.fulfilled, (state, action) => {
        state.loading = false
      })
      .addCase(CompleteTask.rejected, (state) => {
        state.loading = false
      })
      .addCase(DeleteTask.pending, (state) => {
        state.loading = true
      })
      .addCase(DeleteTask.fulfilled, (state, action) => {
        state.loading = false
      })
      .addCase(DeleteTask.rejected, (state) => {
        state.loading = false
      })
      .addCase(GetTask.pending, (state) => {
        state.loading = true
      })
      .addCase(GetTask.fulfilled, (state, action) => {
        state.loading = false
        state.userTasks = action.payload.reverse()
      })
      .addCase(GetTask.rejected, (state) => {
        state.loading = false
      })
      .addCase(AddTodoToTask.pending, (state) => {
        state.loading = true
      })
      .addCase(AddTodoToTask.fulfilled, (state, action) => {
        state.loading = false
      })
      .addCase(AddTodoToTask.rejected, (state) => {
        state.loading = false
      })
      .addCase(CompleteTodoTask.pending, (state) => {
        state.loading = true
      })
      .addCase(CompleteTodoTask.fulfilled, (state, action) => {
        state.loading = false
      })
      .addCase(CompleteTodoTask.rejected, (state) => {
        state.loading = false
      })
      .addCase(UpdateTodoTask.pending, (state) => {
        state.loading = true
      })
      .addCase(UpdateTodoTask.fulfilled, (state, action) => {
        state.loading = false
      })
      .addCase(UpdateTodoTask.rejected, (state) => {
        state.loading = false
      })
      .addCase(DeleteTodoTask.pending, (state) => {
        state.loading = true
      })
      .addCase(DeleteTodoTask.fulfilled, (state, action) => {
        state.loading = false
      })
      .addCase(DeleteTodoTask.rejected, (state) => {
        state.loading = false
      })
  },
})
export const { EmptyTask } = TaskSlice.actions
export default TaskSlice
