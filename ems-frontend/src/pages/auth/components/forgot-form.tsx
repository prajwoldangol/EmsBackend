import React, { useState } from 'react'
import { useForm, FormProvider, Controller } from 'react-hook-form'
import { zodResolver } from '@hookform/resolvers/zod'
import { z } from 'zod'
import { useDispatch, useSelector } from 'react-redux'
import { UpdateEmployeePassword, UserLogin } from '@/store/slice/userSlice'
import { useNavigate, useSearchParams } from 'react-router-dom'
import { AppDispatch, RootState } from '@/store/store'
import { unwrapResult } from '@reduxjs/toolkit'
import { PasswordResetDto } from '@/data/userData'

const strongPasswordRegex = /^(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*])/
// Define the schema using zod
const schema = z
  .object({
    password: z
      .string()
      .min(1, {
        message: 'Please enter your password',
      })
      .min(7, {
        message: 'Password must be at least 7 characters long',
      })
      .refine((value) => strongPasswordRegex.test(value), {
        message:
          'Password must contain at least one uppercase letter, one number, and one special character.',
      }),
    confirmPassword: z.string(),
  })
  .refine((data) => data.password === data.confirmPassword, {
    message: "Passwords don't match.",
    path: ['confirmPassword'],
  })

// Define the form values type based on the schema
type FormValues = z.infer<typeof schema>

export const ForgotPasswordForm: React.FC = () => {
  const [err, setErr] = useState<string>('')
  const [redirect, setRedirect] = useState<boolean>(false)
  // Initialize the form methods using react-hook-form and zodResolver
  const methods = useForm<FormValues>({
    resolver: zodResolver(schema),
  })
  const [searchId] = useSearchParams()
  const id: string | null = searchId.get('id')
  const navigate = useNavigate()
  const dispatch = useDispatch<AppDispatch>()
  // const userRole = useSelector((state: RootState) => state.users.role)
  // Handle form submission
  const onSubmit = async (data: FormValues) => {
    const credentials: PasswordResetDto = {
      newPassword: data.password,
      empId: id,
    }
    const update = await dispatch(UpdateEmployeePassword(credentials))
    // const user = await dispatch(UserLogin(credentials))
    console.log(update)
    console.log(credentials)
    if (!update.error && update.payload.error != '500') {
      setErr('')
      setRedirect(true)
      methods.reset()
      setTimeout(() => {
        navigate('/employee-login')
      }, 3000)
      return
    }
    setErr('Failed To update password')
    // console.log(data)
    // if (user.payload.role !== 'USER') {
    //   navigate('/dashboard')
    //   return
    // }
  }

  return (
    <div className=''>
      {redirect && (
        <div className='fixed inset-0 z-50 flex flex-col items-center justify-center bg-white bg-opacity-75 dark:bg-black dark:bg-opacity-75'>
          <p className='text-2xl font-semibold '>
            Password Updated Successfully !!
          </p>
          <p className='text-2xl font-semibold '>
            Redirecting to login page....
          </p>
        </div>
      )}
      <FormProvider {...methods}>
        <form onSubmit={methods.handleSubmit(onSubmit)} className='grid gap-3'>
          {err && <p className='text-red-500'>{err}</p>}
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
                    <p className='text-red-500'>{fieldState.error.message}</p>
                  )}
                </div>
              )}
            />
          </div>
          <div>
            <label htmlFor='confirmPassword'>Confirm Password</label>
            <Controller
              name='confirmPassword'
              control={methods.control}
              render={({ field, fieldState }) => (
                <div>
                  <input
                    id='confirmPassword'
                    type='password'
                    {...field}
                    className='flex h-9 w-full rounded-md border border-input bg-transparent px-3 py-1 text-sm shadow-sm transition-colors file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-1 focus-visible:ring-ring disabled:cursor-not-allowed disabled:opacity-50'
                    value={field.value || ''}
                  />
                  {fieldState.error && (
                    <p className='text-red-500'>{fieldState.error.message}</p>
                  )}
                </div>
              )}
            />
          </div>
          <button className='mt-4 h-12 w-48 justify-center rounded-lg bg-[#206fed] text-lg font-bold uppercase text-[#ffffff]'>
            Reset Password
          </button>
        </form>
      </FormProvider>
    </div>
  )
}
