// // import { HTMLAttributes, useState } from 'react'
// // import { useForm } from 'react-hook-form'
// // import { Link } from 'react-router-dom'
// // import { z } from 'zod'
// // import { zodResolver } from '@hookform/resolvers/zod'
// // // import { IconBrandFacebook, IconBrandGithub } from '@tabler/icons-react'
// // import {
// //   Form,
// //   FormControl,
// //   FormField,
// //   FormItem,
// //   FormLabel,
// //   FormMessage,
// // } from '@/components/ui/form'
// // import { Input } from '@/components/ui/input'
// // // import { Button } from '@/components/custom/button'
// // // import { PasswordInput } from '@/components/custom/password-input'
// // import { cn } from '@/lib/utils'

// // interface UserAuthFormProps extends HTMLAttributes<HTMLDivElement> {}

// // const formSchema = z.object({
// //   email: z
// //     .string()
// //     .min(1, { message: 'Please enter your email' })
// //     .email({ message: 'Invalid email address' }),
// //   password: z
// //     .string()
// //     .min(1, {
// //       message: 'Please enter your password',
// //     })
// //     .min(7, {
// //       message: 'Password must be at least 7 characters long',
// //     }),
// // })

// // export function UserAuthForm({ className, ...props }: UserAuthFormProps) {
// //   const [isLoading, setIsLoading] = useState(false)

// //   const form = useForm<z.infer<typeof formSchema>>({
// //     resolver: zodResolver(formSchema),
// //     defaultValues: {
// //       email: '',
// //       password: '',
// //     },
// //   })

// //   function onSubmit(data: z.infer<typeof formSchema>) {
// //     setIsLoading(true)
// //     console.log(data)

// //     setTimeout(() => {
// //       setIsLoading(false)
// //     }, 3000)
// //   }

// //   return (
// //     <div className={cn('grid gap-6', className)} {...props}>
// //       <Form {...form}>
// //         <form onSubmit={form.handleSubmit(onSubmit)}>
// //           <div className='grid gap-2'>
// //             <FormField
// //               control={form.control}
// //               name='email'
// //               render={({ field }) => (
// //                 <FormItem className='space-y-1'>
// //                   <FormLabel>Email</FormLabel>
// //                   <FormControl>
// //                     <Input placeholder='name@example.com' {...field} />
// //                   </FormControl>
// //                   <FormMessage />
// //                 </FormItem>
// //               )}
// //             />
// //             <FormField
// //               control={form.control}
// //               name='password'
// //               render={({ field }) => (
// //                 <FormItem className='space-y-1'>
// //                   <div className='flex items-center justify-between'>
// //                     <FormLabel>Password</FormLabel>
// //                     <Link
// //                       to='/forgot-password'
// //                       className='text-sm font-medium text-muted-foreground hover:opacity-75'
// //                     >
// //                       Forgot password?
// //                     </Link>
// //                   </div>
// //                   <FormControl>
// //                     <Input placeholder='********' {...field} />
// //                   </FormControl>
// //                   <FormMessage />
// //                 </FormItem>
// //               )}
// //             />
// //             {/* <Button className='mt-2' loading={isLoading}>
// //               Login
// //             </Button> */}
// //           </div>
// //         </form>
// //       </Form>
// //     </div>
// //   )
// // }

// import React from 'react'
// import { useForm, FormProvider, Controller } from 'react-hook-form'
// import { zodResolver } from '@hookform/resolvers/zod'
// import { z } from 'zod'
// import { useDispatch } from 'react-redux'
// import { UserLogin } from '@/store/slice/userSlice'
// import { useNavigate } from 'react-router-dom'
// import { AppDispatch } from '@/store/store'

// // Define the schema using zod
// const schema = z.object({
//   email: z
//     .string()
//     .min(1, { message: 'Please enter your email' })
//     .email({ message: 'Invalid email address' }),
//   // username: z
//   //   .string()
//   //   .min(1, { message: 'Username is Required' })
//   //   .email({ message: 'Invalid email address' }),
//   password: z.string().min(2, {
//     message: 'Please enter your password',
//   }),
//   // password: z.string().min(6, { message: 'Password must be at least 6 digit long' }),
//   // number: z.number().min(1, 'Number is required'),
//   // email: z.string().email('Invalid email address'),
//   // textarea: z.string().min(1, 'This field is required'),
// })

// // Define the form values type based on the schema
// type FormValues = z.infer<typeof schema>

// export const UserAuthForm: React.FC = () => {
//   // Initialize the form methods using react-hook-form and zodResolver
//   const methods = useForm<FormValues>({
//     resolver: zodResolver(schema),
//   })
//   const navigate = useNavigate()
//   const dispatch = useDispatch<AppDispatch>()
//   // Handle form submission
//   const onSubmit = async (data: FormValues) => {
//     const credentials: string[] = [data.email, data.password]
//     const user = await dispatch(UserLogin(credentials))

//     if (!user.payload.error) {
//       navigate('/dashboard')
//     }
//     console.log(data)
//   }

//   return (
//     <div className=''>
//       <FormProvider {...methods}>
//         <form onSubmit={methods.handleSubmit(onSubmit)} className='grid gap-3'>
//           <div>
//             <label htmlFor='email'>Username</label>
//             <Controller
//               name='email'
//               control={methods.control}
//               render={({ field, fieldState }) => (
//                 <div>
//                   <input
//                     id='email'
//                     {...field}
//                     className='flex h-9 w-full rounded-md border border-input bg-transparent px-3 py-1 text-sm shadow-sm transition-colors file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-1 focus-visible:ring-ring disabled:cursor-not-allowed disabled:opacity-50'
//                   />
//                   {fieldState.error && (
//                     <p style={{ color: 'red' }}>{fieldState.error.message}</p>
//                   )}
//                 </div>
//               )}
//             />
//           </div>

//           <div>
//             <label htmlFor='password'>Password</label>
//             <Controller
//               name='password'
//               control={methods.control}
//               render={({ field, fieldState }) => (
//                 <div>
//                   <input
//                     id='password'
//                     type='password'
//                     {...field}
//                     className='flex h-9 w-full rounded-md border border-input bg-transparent px-3 py-1 text-sm shadow-sm transition-colors file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-1 focus-visible:ring-ring disabled:cursor-not-allowed disabled:opacity-50'
//                   />
//                   {fieldState.error && (
//                     <p style={{ color: 'red' }}>{fieldState.error.message}</p>
//                   )}
//                 </div>
//               )}
//             />
//           </div>

//           <button className='mt-4 h-12 w-48 justify-center rounded-lg bg-primary text-lg font-bold uppercase text-[#ffffff] transition-colors hover:bg-green-400'>
//             Log In
//           </button>
//         </form>
//       </FormProvider>
//     </div>
//   )
// }
