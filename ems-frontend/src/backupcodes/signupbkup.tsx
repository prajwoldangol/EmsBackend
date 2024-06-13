// // import { HTMLAttributes, useState } from 'react'
// // import { useForm } from 'react-hook-form'
// // import { zodResolver } from '@hookform/resolvers/zod'
// // // import { IconBrandFacebook, IconBrandGithub } from '@tabler/icons-react'
// // import { z } from 'zod'
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

// // interface SignUpFormProps extends HTMLAttributes<HTMLDivElement> {}
// // const strongPasswordRegex = /^(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*])/
// // const formSchema = z
// //   .object({
// //     email: z
// //       .string()
// //       .min(1, { message: 'Please enter your email' })
// //       .email({ message: 'Invalid email address' }),
// //     password: z
// //       .string()
// //       .min(1, {
// //         message: 'Please enter your password',
// //       })
// //       .min(7, {
// //         message: 'Password must be at least 7 characters long',
// //       })
// //       .refine((value) => strongPasswordRegex.test(value), {
// //         message:
// //           'Password must contain at least one uppercase letter, one number, and one special character.',
// //       }),
// //     confirmPassword: z.string(),
// //   })
// //   .refine((data) => data.password === data.confirmPassword, {
// //     message: "Passwords don't match.",
// //     path: ['confirmPassword'],
// //   })

// // export function SignUpForm({ className, ...props }: SignUpFormProps) {
// //   const [isLoading, setIsLoading] = useState(false)

// //   const form = useForm<z.infer<typeof formSchema>>({
// //     resolver: zodResolver(formSchema),
// //     defaultValues: {
// //       email: '',
// //       password: '',
// //       confirmPassword: '',
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
// //                   <FormLabel>Password</FormLabel>
// //                   <FormControl>
// //                     <Input placeholder='********' {...field} />
// //                   </FormControl>
// //                   <FormMessage />
// //                 </FormItem>
// //               )}
// //             />
// //             <FormField
// //               control={form.control}
// //               name='confirmPassword'
// //               render={({ field }) => (
// //                 <FormItem className='space-y-1'>
// //                   <FormLabel>Confirm Password</FormLabel>
// //                   <FormControl>
// //                     <Input placeholder='********' {...field} />
// //                   </FormControl>
// //                   <FormMessage />
// //                 </FormItem>
// //               )}
// //             />
// //             {/* <Button className='mt-2 bg-blue-600 hover:bg-blue-400' loading={isLoading}>
// //               Create Account
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
// const strongPasswordRegex = /^(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*])/
// // Define the schema using zod
// const schema = z
//   .object({
//     email: z
//       .string()
//       .min(4, { message: 'Username Is Required' })
//       .email({ message: 'Username Must be an email.' }),
//     password: z
//       .string()
//       .min(1, {
//         message: 'Please enter your password',
//       })
//       .min(7, {
//         message: 'Password must be at least 7 characters long',
//       })
//       .refine((value) => strongPasswordRegex.test(value), {
//         message:
//           'Password must contain at least one uppercase letter, one number, and one special character.',
//       }),
//     confirmPassword: z.string(),
//     phone: z.string().regex(/^\d{10}$/, {
//       message: 'Phone number must be exactly 10 digits',
//     }),
//   })
//   .refine((data) => data.password === data.confirmPassword, {
//     message: "Passwords don't match.",
//     path: ['confirmPassword'],
//   })

// // Define the form values type based on the schema
// type FormValues = z.infer<typeof schema>

// export const SignUpForm: React.FC = () => {
//   // Initialize the form methods using react-hook-form and zodResolver
//   const methods = useForm<FormValues>({
//     resolver: zodResolver(schema),
//   })

//   // Handle form submission
//   const onSubmit = (data: FormValues) => {
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
//                     <p className='text-red-500'>{fieldState.error.message}</p>
//                   )}
//                 </div>
//               )}
//             />
//           </div>
//           <div>
//             <label htmlFor='phone'>Phone Number</label>
//             <Controller
//               name='phone'
//               control={methods.control}
//               render={({ field, fieldState }) => (
//                 <div>
//                   <input
//                     id='phone'
//                     {...field}
//                     className='flex h-9 w-full rounded-md border border-input bg-transparent px-3 py-1 text-sm shadow-sm transition-colors file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-1 focus-visible:ring-ring disabled:cursor-not-allowed disabled:opacity-50'
//                   />
//                   {fieldState.error && (
//                     <p className='text-red-500'>{fieldState.error.message}</p>
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
//                     <p className='text-red-500'>{fieldState.error.message}</p>
//                   )}
//                 </div>
//               )}
//             />
//           </div>
//           <div>
//             <label htmlFor='confirmPassword'>Confirm Password</label>
//             <Controller
//               name='confirmPassword'
//               control={methods.control}
//               render={({ field, fieldState }) => (
//                 <div>
//                   <input
//                     id='confirmPassword'
//                     type='password'
//                     {...field}
//                     className='flex h-9 w-full rounded-md border border-input bg-transparent px-3 py-1 text-sm shadow-sm transition-colors file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-1 focus-visible:ring-ring disabled:cursor-not-allowed disabled:opacity-50'
//                   />
//                   {fieldState.error && (
//                     <p className='text-red-500'>{fieldState.error.message}</p>
//                   )}
//                 </div>
//               )}
//             />
//           </div>
//           <button className='mt-4 h-12 w-48 justify-center rounded-lg bg-[#206fed] text-lg font-bold uppercase text-[#ffffff]'>
//             Sign up
//           </button>
//         </form>
//       </FormProvider>
//     </div>
//   )
// }
