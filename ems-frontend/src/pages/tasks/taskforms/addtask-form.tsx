// // import { zodResolver } from '@hookform/resolvers/zod'
// // import { CalendarIcon } from '@radix-ui/react-icons'
// // import { useFieldArray, useForm } from 'react-hook-form'
// // import { z } from 'zod'
// // import dayjs from 'dayjs'
// // import { cn } from '@/lib/utils'
// // import { Button } from '@/components/custom/button'
// // import { Calendar } from '@/components/ui/calendar'
// // import { Switch } from '@/components/ui/switch'
// // import {
// //   Form,
// //   FormControl,
// //   FormField,
// //   FormItem,
// //   FormLabel,
// //   FormMessage,
// // } from '@/components/ui/form'
// // import { Input } from '@/components/ui/input'
// // import {
// //   Popover,
// //   PopoverContent,
// //   PopoverTrigger,
// // } from '@/components/ui/popover'
// // import { toast } from '@/components/ui/use-toast'
// // import { useState } from 'react'
// // import { Textarea } from '@/components/ui/textarea'
// // import TextEditor from '@/components/text-editor'

// export function TaskForm() {
//   // const accountFormSchema = z.object({
//   //   name: z.string().min(5, {
//   //     message: 'Task must be at least 5 characters.',
//   //   }),
//   //   description: z.string().min(10, {
//   //     message: 'Task must be at least 10 characters.',
//   //   }),
//   //   dob: z.date({
//   //     required_error: 'A date of birth is required.',
//   //   }),
//   //   communication_emails: z.boolean(),
//   //   urls: z
//   //     .array(
//   //       z.object({
//   //         value: z.string().url({ message: 'Please enter a valid URL.' }),
//   //       })
//   //     )
//   //     .optional(),
//   // })
//   // const profileFormSchema = z.object({
//   //   urls: z
//   //     .array(
//   //       z.object({
//   //         value: z.string().url({ message: 'Please enter a valid URL.' }),
//   //       })
//   //     )
//   //     .optional(),
//   // })
//   // type ProfileFormValues = z.infer<typeof accountFormSchema>

//   // // This can come from your database or API.
//   // const defaultValues: Partial<ProfileFormValues> = {
//   //   urls: [{ value: 'https://shadcn.com' }],
//   // }

//   // type AccountFormValues = z.infer<typeof accountFormSchema>

//   // This can come from your database or API.
//   // const defaultValues2: Partial<AccountFormValues> = {
//   //   name: 'Your name',
//   //   dob: new Date('2023-01-23'),
//   // }
//   // const form2 = useForm<ProfileFormValues>({
//   //   resolver: zodResolver(accountFormSchema),
//   //   defaultValues,
//   //   mode: 'onChange',
//   // })
//   // const form = useForm<AccountFormValues>({
//   //   resolver: zodResolver(accountFormSchema),
//   //   defaultValues,
//   // })
//   // const { fields, append } = useFieldArray({
//   //   name: 'urls',
//   //   control: form.control,
//   // })
//   // // const [test, setTest] = useState('test')

//   // function onSubmit(data: AccountFormValues) {
//   //   toast({
//   //     title: 'You submitted the following values:',
//   //     description: (
//   //       <pre className='mt-2 w-[340px] rounded-md bg-slate-950 p-4'>
//   //         <code className='text-white'>{JSON.stringify(data, null, 2)}</code>
//   //       </pre>
//   //     ),
//   //   })
//   // setTest(data.name)
//   // console.log(test)

//   return (
//     <></>
//     // <Form {...form}>
//     //   <form onSubmit={form.handleSubmit(onSubmit)} className=''>
//     //     <FormField
//     //       control={form.control}
//     //       name='name'
//     //       render={({ field }) => (
//     //         <FormItem className='my-8'>
//     //           <FormLabel>Task Title</FormLabel>
//     //           <FormControl>
//     //             <Input placeholder='Enter Task Title' {...field} />
//     //           </FormControl>
//     //           <FormMessage />
//     //         </FormItem>
//     //       )}
//     //     />
//     //     <FormField
//     //       control={form.control}
//     //       name='description'
//     //       render={({ field }) => (
//     //         <FormItem>
//     //           <FormLabel>Your Task Details Here</FormLabel>
//     //           <FormControl className=''>
//     //             <Textarea placeholder='Enter Task Details ' {...field} />
//     //           </FormControl>
//     //           <FormMessage />
//     //         </FormItem>
//     //       )}
//     //     />
//     //     <FormField
//     //       control={form.control}
//     //       name='communication_emails'
//     //       render={({ field }) => (
//     //         <FormItem className='flex flex-row items-center justify-between rounded-lg border p-4'>
//     //           <div className='space-y-0.5'>
//     //             <FormLabel className='text-base'>
//     //               Communication emails
//     //             </FormLabel>
//     //           </div>
//     //           <FormControl>
//     //             <Switch
//     //               checked={field.value}
//     //               onCheckedChange={field.onChange}
//     //             />
//     //           </FormControl>
//     //         </FormItem>
//     //       )}
//     //     />

//     //     <div>
//     //       {fields.map((field, index) => (
//     //         <FormField
//     //           control={form2.control}
//     //           key={field.id}
//     //           name={`urls.${index}.value`}
//     //           render={({ field }) => (
//     //             <FormItem>
//     //               <FormLabel className={cn(index !== 0 && 'sr-only')}>
//     //                 URLs
//     //               </FormLabel>

//     //               <FormControl>
//     //                 <Input {...field} />
//     //               </FormControl>
//     //               <FormMessage />
//     //             </FormItem>
//     //           )}
//     //         />
//     //       ))}
//     /* <Button
//             type='button'
//             variant='outline'
//             size='sm'
//             className='mt-2'
//             onClick={() => append({ value: '' })}
//           >
//             Add URL
//           </Button> */
//     // </div>
//     /* <FormField
//           control={form.control}
//           name='description'
//           render={({ field }) => (
//             <FormItem className='my-8'>
//               <FormLabel>Your Task Details Here</FormLabel>
//               <FormControl>
//                 <TextEditor placeholder='Enter Details' {...field} />
//               </FormControl>
//               <FormMessage />
//             </FormItem>
//           )}
//         /> */
//     /* <FormField
//           control={form.control}
//           name='dob'
//           render={({ field }) => (
//             <FormItem className='mb-4  flex flex-col '>
//               <FormLabel>Task Deadline</FormLabel>
//               <Popover>
//                 <PopoverTrigger asChild>
//                   <FormControl>
//                     <Button
//                       variant={'outline'}
//                       className={cn(
//                         'w-[240px] pl-3 text-left font-normal',
//                         !field.value && 'text-muted-foreground'
//                       )}
//                     >
//                       {field.value ? (
//                         dayjs(field.value).format('MMM D, YYYY')
//                       ) : (
//                         <span>Pick a date</span>
//                       )}
//                       <CalendarIcon className='ml-auto h-4 w-4 opacity-50' />
//                     </Button>
//                   </FormControl>
//                 </PopoverTrigger>
//                 <PopoverContent className='w-auto p-0' align='start'>
//                   <Calendar
//                     mode='single'
//                     selected={field.value}
//                     onSelect={field.onChange}
//                     disabled={(date: Date) =>
//                       date > new Date() || date < new Date('1900-01-01')
//                     }
//                     initialFocus
//                   />
//                 </PopoverContent>
//               </Popover>

//               <FormMessage />
//             </FormItem>
//           )}
//         /> */

//     /* <Button type='submit' className='bg-blue-500 hover:bg-blue-400'>
//           Add New Task
//         </Button> */
//     //   </form>
//     // </Form>
//   )
// }

import React, { useEffect, useState } from 'react'
import { useForm, FormProvider, Controller } from 'react-hook-form'
import { zodResolver } from '@hookform/resolvers/zod'
import { z } from 'zod'
import { IconX } from '@tabler/icons-react'

const schema = z.object({
  boardName: z.string().min(5, 'Board Name must be minimum 5 characters long'),
  boardAgenda: z
    .array(
      z.object({
        agenda: z.string(),
      })
    )
    .optional(),
  boardTeam: z.string().optional(),
})

// Define the form values type based on the schema
type FormValues = z.infer<typeof schema>

export const TaskForm: React.FC = () => {
  const [boardAgenda, setBoardAgenda] = useState<{ agenda: string }[]>([
    { agenda: '' },
  ])

  const handleClick: React.MouseEventHandler<HTMLButtonElement> = (e) => {
    e.preventDefault()
    if (boardAgenda.length > 4) {
      alert(' Only 5 Agenda Are Allowed')
      console.log(boardAgenda)
      return
    }
    setBoardAgenda([...boardAgenda, { agenda: '' }])
  }
  const handleChange = (e: React.ChangeEvent<HTMLInputElement>, i: number) => {
    const { name, value } = e.target
    const updatedAgendas = [...boardAgenda]
    updatedAgendas[i] = { agenda: value } // Update the agenda at index i
    setBoardAgenda(updatedAgendas)
  }

  const handleDelete = (i: number) => {
    const updatedAgendas = boardAgenda.filter((_, index) => index !== i)
    setBoardAgenda(updatedAgendas)
  }

  // Initialize the form methods using react-hook-form and zodResolver
  const methods = useForm<FormValues>({
    resolver: zodResolver(schema),
  })
  // const { reset } = useForm<FormValues>()
  // Handle form submission
  const onSubmit = (data: FormValues) => {
    // Merge boardAgenda from local state with form data
    const formDataWithAgenda = {
      ...data,
      boardAgenda: boardAgenda.map((item) => item.agenda),
    }
    console.log(formDataWithAgenda)
    methods.reset({
      boardName: '',
      boardTeam: '',
    })
    setBoardAgenda([{ agenda: '' }])
  }
  // useEffect(() => {
  //   const handleKeyPress = (e: KeyboardEvent) => {
  //     if (e.key === 'Enter') {
  //       // Prevent Enter key press from adding new fields when not focused on a dynamic field
  //       const activeElement = document.activeElement as HTMLElement
  //       const isDynamicField =
  //         activeElement && activeElement.id.startsWith('boardAgenda-')
  //       if (!isDynamicField) {
  //         e.preventDefault()
  //       }
  //     }
  //   }

  //   window.addEventListener('keydown', handleKeyPress)

  //   return () => {
  //     window.removeEventListener('keydown', handleKeyPress)
  //   }
  // }, [])

  const handleEnterKey = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === 'Enter') {
      e.preventDefault()
      e.stopPropagation()
    }
  }
  return (
    <div className='grid grid-cols-2 '>
      <div className='rounded-md bg-gray-50 p-10 shadow-[rgba(17,_17,_26,_0.1)_0px_0px_16px]'>
        <FormProvider {...methods}>
          <form
            onSubmit={methods.handleSubmit(onSubmit)}
            className='grid gap-3'
          >
            <div>
              <label htmlFor='boardName'>Board Name</label>
              <Controller
                name='boardName'
                control={methods.control}
                render={({ field, fieldState }) => (
                  <div>
                    <input
                      id='boardName'
                      {...field}
                      className='flex h-9 w-full rounded-md border border-input bg-transparent px-3 py-1 text-sm shadow-sm transition-colors file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-1 focus-visible:ring-ring disabled:cursor-not-allowed disabled:opacity-50'
                      // onKeyDown={(e) => {
                      //   e.preventDefault()
                      //   e.stopPropagation() // Stop event propagation to prevent interference with other elements
                      // }}
                      onKeyDown={handleEnterKey}
                    />
                    {fieldState.error && (
                      <p className='text-red-500'>{fieldState.error.message}</p>
                    )}
                  </div>
                )}
              />
            </div>

            <div>
              <label htmlFor='boardAgenda'>Board Agenda</label>
              <Controller
                name='boardAgenda'
                control={methods.control}
                render={({ field, fieldState }) => (
                  <div>
                    {boardAgenda.map((val, i) => (
                      <div
                        key={i}
                        className='flex flex-row items-center justify-center gap-3'
                      >
                        <input
                          id={`boardAgenda-${i}`}
                          type='text'
                          {...field}
                          className='my-2 flex h-9 w-full rounded-md border border-input bg-transparent px-3 py-1 text-sm shadow-sm transition-colors file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-1 focus-visible:ring-ring disabled:cursor-not-allowed disabled:opacity-50'
                          value={val.agenda}
                          onChange={(e) => handleChange(e, i)}
                          // onKeyDown={(e) => {
                          //   e.preventDefault()
                          //   e.stopPropagation() // Stop event propagation to prevent interference with other elements
                          // }}
                          onKeyDown={handleEnterKey}
                        />
                        {boardAgenda.length > 1 && (
                          <IconX
                            size={28}
                            color='white'
                            onClick={() => handleDelete(i)}
                            className='cursor-pointer rounded-full bg-red-600 p-1'
                          />
                        )}
                      </div>
                    ))}
                  </div>
                )}
              />

              <button
                className='mt-4 justify-center rounded-lg bg-green-400 px-4 py-2 text-sm font-bold uppercase text-[#0d0303] transition-colors hover:bg-green-300'
                onClick={handleClick}
              >
                + Add Agenda
              </button>
            </div>

            <div>
              <label htmlFor='boardTeam'>Assign To</label>
              <Controller
                name='boardTeam'
                control={methods.control}
                render={({ field, fieldState }) => (
                  <div>
                    <select
                      id='boardTeam'
                      {...field}
                      className='flex h-9 w-full rounded-md border border-input bg-transparent px-3 py-1 text-sm shadow-sm transition-colors file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-1 focus-visible:ring-ring disabled:cursor-not-allowed disabled:opacity-50'
                    >
                      <option value='none'> None </option>
                      <option value='test'> Test </option>
                    </select>
                    {fieldState.error && (
                      <p className='text-red-500'>{fieldState.error.message}</p>
                    )}
                  </div>
                )}
              />
            </div>
            <button className='mt-4 h-12 w-48 justify-center rounded-lg bg-yellow-400 text-lg font-bold uppercase text-[#0d0303] transition-colors hover:bg-yellow-300'>
              Add New Board
            </button>
          </form>
        </FormProvider>
      </div>
    </div>
  )
}
