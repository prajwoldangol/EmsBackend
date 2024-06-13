import React, { useState } from 'react'
import { useForm, FormProvider, Controller } from 'react-hook-form'
import { zodResolver } from '@hookform/resolvers/zod'
import { z } from 'zod'
import { useNavigate } from 'react-router-dom'
import { useDispatch, useSelector } from 'react-redux'
import { EmployerSignupDto } from '@/data/userData'
import { EmployerRegister } from '@/store/slice/userSlice'
import { AppDispatch, RootState } from '@/store/store'

const strongPasswordRegex = /^(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*])/
// Define the schema using zod
const schema = z
  .object({
    email: z
      .string()
      .min(4, { message: 'Username Is Required' })
      .email({ message: 'Username Must be an email.' }),
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
    phone: z.string().regex(/^\d{10}$/, {
      message: 'Phone number must be exactly 10 digits',
    }),
  })
  .refine((data) => data.password === data.confirmPassword, {
    message: "Passwords don't match.",
    path: ['confirmPassword'],
  })

// Define the form values type based on the schema
type FormValues = z.infer<typeof schema>

export const SignUpForm: React.FC = () => {
  // Initialize the form methods using react-hook-form and zodResolver
  const methods = useForm<FormValues>({
    resolver: zodResolver(schema),
  })
  const [registrationError, setRegistrationError] = useState<string>('')
  const navigate = useNavigate()
  const dispatch = useDispatch<AppDispatch>()
  // const errors = useSelector((state: RootState) => state.users.error)
  // Handle form submission
  const onSubmit = async (data: FormValues) => {
    setRegistrationError('')
    const signUpData: EmployerSignupDto = {
      username: data.email,
      password: data.password,
      phone: data.phone,
    }
    const user = await dispatch(EmployerRegister(signUpData))
    console.log(user)
    if (user.payload.error === '500') {
      methods.reset()
      // console.log(errors)
      setRegistrationError(
        'Duplicate Account Detected. Please Use Unique Email And Phone!'
      )
      return
    }
    console.log(data)
    navigate('/login')
  }

  return (
    <div className=''>
      <FormProvider {...methods}>
        {registrationError && (
          <p className='text-red-500'>{registrationError}</p>
        )}
        <form onSubmit={methods.handleSubmit(onSubmit)} className='grid gap-3'>
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
                    <p className='text-red-500'>{fieldState.error.message}</p>
                  )}
                </div>
              )}
            />
          </div>
          <div>
            <label htmlFor='phone'>Phone Number</label>
            <Controller
              name='phone'
              control={methods.control}
              render={({ field, fieldState }) => (
                <div>
                  <input
                    id='phone'
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
            Sign up
          </button>
        </form>
      </FormProvider>
    </div>
  )
}
