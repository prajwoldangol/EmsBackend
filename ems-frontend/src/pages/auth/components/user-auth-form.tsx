import React, { useState } from 'react'
import { useForm, FormProvider, Controller } from 'react-hook-form'
import { zodResolver } from '@hookform/resolvers/zod'
import { z } from 'zod'
import { useDispatch, useSelector } from 'react-redux'
import { UserLogin } from '@/store/slice/userSlice'
import { useNavigate } from 'react-router-dom'
import { AppDispatch, RootState } from '@/store/store'
import { unwrapResult } from '@reduxjs/toolkit'
import { EmployeeLogin } from '@/store/slice/employeeSlice'

// Define the schema using zod
const schema = z.object({
  email: z
    .string()
    .min(1, { message: 'Please enter your email' })
    .email({ message: 'Invalid email address' }),
  password: z.string().min(2, {
    message: 'Please enter your password',
  }),
})

// Define the form values type based on the schema
type FormValues = z.infer<typeof schema>
interface LoginFormProps {
  loginType: 'employee' | 'employer'
}

export const UserAuthForm: React.FC<LoginFormProps> = ({ loginType }) => {
  const [login, setLogin] = useState<string>('')
  // Initialize the form methods using react-hook-form and zodResolver
  const methods = useForm<FormValues>({
    resolver: zodResolver(schema),
    defaultValues: {
      email: '',
      password: '',
    },
  })
  const navigate = useNavigate()
  const dispatch = useDispatch<AppDispatch>()
  // const userRole = useSelector((state: RootState) => state.users.role)
  // Handle form submission
  const onSubmit = async (data: FormValues) => {
    const credentials: string[] = [data.email, data.password]
    const user = await dispatch(UserLogin(credentials))
    console.log('employer')
    console.log(user)
    if (user.payload.statusCode === 401) {
      methods.reset()
      setLogin('Username or Password donot match!')

      return
    }
    console.log(data)
    if (user.payload.role !== 'USER') {
      navigate('/dashboard')
      return
    }
    navigate('/#plan')
  }
  const loginEmployee = async (data: FormValues) => {
    const credentials: string[] = [data.email, data.password]
    const user = await dispatch(EmployeeLogin(credentials))
    console.log('employee')
    console.log(user)
    if (user.payload.statusCode === 401) {
      methods.reset()
      setLogin('Username or Password donot match!')

      return
    }
    console.log(data)

    navigate('/employee-dashboard')
  }
  return (
    <div className=''>
      <FormProvider {...methods}>
        <form
          onSubmit={
            loginType === 'employer'
              ? methods.handleSubmit(onSubmit)
              : methods.handleSubmit(loginEmployee)
          }
          className='grid gap-3'
        >
          {login && <p style={{ color: 'red' }}>{login}</p>}
          <div>
            <label htmlFor='email'>Username</label>
            <Controller
              name='email'
              control={methods.control}
              render={({ field, fieldState }) => (
                <div>
                  <input
                    id='email'
                    {...field}
                    className='flex h-9 w-full rounded-md border border-input bg-transparent px-3 py-1 text-sm shadow-sm transition-colors file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-1 focus-visible:ring-ring disabled:cursor-not-allowed disabled:opacity-50'
                    value={field.value || ''}
                  />
                  {fieldState.error && (
                    <p style={{ color: 'red' }}>{fieldState.error.message}</p>
                  )}
                </div>
              )}
            />
          </div>

          <div>
            <label htmlFor='password'>Password</label>
            <Controller
              name='password'
              control={methods.control}
              render={({ field, fieldState }) => (
                <div>
                  <input
                    id='password'
                    type='password'
                    {...field}
                    className='flex h-9 w-full rounded-md border border-input bg-transparent px-3 py-1 text-sm shadow-sm transition-colors file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-1 focus-visible:ring-ring disabled:cursor-not-allowed disabled:opacity-50'
                    value={field.value || ''}
                  />
                  {fieldState.error && (
                    <p style={{ color: 'red' }}>{fieldState.error.message}</p>
                  )}
                </div>
              )}
            />
          </div>

          <button className='mt-4 h-12 w-48 justify-center rounded-lg bg-primary text-lg font-bold uppercase text-[#ffffff] transition-colors hover:bg-green-400'>
            Log In
          </button>
        </form>
      </FormProvider>
    </div>
  )
}
