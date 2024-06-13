// import React, { useState } from 'react'
// import { useForm, FormProvider, Controller } from 'react-hook-form'
// import { zodResolver } from '@hookform/resolvers/zod'
// import { z } from 'zod'
// // import { IconX } from '@tabler/icons-react'

// import DateInput2 from '@/components/ui/date'
// import { addDays, format } from 'date-fns'
// import { UserNav } from '@/components/user-nav'
// import TimeInput from '@/components/ui/timepicker'
// const schema = z.object({})

// // Define the form values type based on the schema
// type FormValues = z.infer<typeof schema>
// const ScheduleForm: React.FC = () => {
//   const methods = useForm<FormValues>({
//     resolver: zodResolver(schema),
//   })

//   const [selectedDate, setSelectedDate] = useState<string | undefined>(
//     undefined
//   )
//   const initialRange = {
//     from: '',
//     to: '',
//   }
//   // const initialRange = {
//   //   from: new Date().toISOString(),
//   //   to: addDays(new Date(), 13).toISOString(),
//   // }
//   const [selectedRange, setSelectedRange] = useState<
//     { from: string | undefined; to: string | undefined } | undefined
//   >(initialRange)
//   const onSubmit = (data: FormValues) => {
//     console.log(data)
//   }
//   // const disabledDays = {
//   //   before: selectedRange?.from,
//   //   after: selectedRange?.to,
//   // }
//   const disabledDays =
//     selectedRange?.from && selectedRange?.to
//       ? {
//           before: new Date(selectedRange.from),
//           after: new Date(selectedRange.to),
//         }
//       : { before: new Date() }
//   const isSinglePickerDisabled = !(selectedRange?.from && selectedRange?.to)
//   return (
//     <div className='container relative flex h-screen w-full flex-col'>
//       <div className=' flex-1 space-y-10 overflow-hidden px-4 py-6 md:px-8'>
//         <div className='flex items-center justify-between space-y-2'>
//           <h1 className='text-2xl font-bold tracking-tight md:text-3xl'>
//             Add new Task Board
//           </h1>
//           <UserNav color='black' />
//         </div>
//         <div className='grid grid-cols-2 '>
//           <div className='rounded-md bg-gray-50 p-10'>
//             <FormProvider {...methods}>
//               <form
//                 onSubmit={methods.handleSubmit(onSubmit)}
//                 className='grid gap-3'
//               >
//                 <div>
//                   <label htmlFor='dateRange'>Schedule Date Range</label>
//                   <Controller
//                     name='dateRange'
//                     control={methods.control}
//                     render={({ field, fieldState }) => (
//                       <div>
//                         <DateInput2
//                           mode='range'
//                           selectedRange={
//                             selectedRange
//                               ? {
//                                   from: selectedRange.from
//                                     ? new Date(selectedRange.from).toISOString()
//                                     : undefined,
//                                   to: selectedRange.to
//                                     ? new Date(selectedRange.to).toISOString()
//                                     : undefined,
//                                 }
//                               : undefined
//                           }
//                           onRangeChange={setSelectedRange}
//                           disabledDays={disabledDays}
//                         />
//                         {fieldState.error && (
//                           <p className='text-red-500'>
//                             {fieldState.error.message}
//                           </p>
//                         )}
//                       </div>
//                     )}
//                   />
//                   {/* <h1>Date Picker Example</h1>

//                   <h2>Single Date Picker</h2> */}

//                   {/* {selectedDate && (
//                     <p>
//                       Selected Date:
//                       {format(new Date(selectedDate), 'MMMM dd yyyy')}
//                     </p>
//                   )} */}

//                   {/* {selectedRange && (
//                     <p>
//                       Selected Range: {selectedRange.from} - {selectedRange.to}
//                     </p>
//                   )} */}
//                 </div>
//                 <div className=' bg-red-100 p-2'>
//                   <label htmlFor='selectDate'>Select Date </label>
//                   <div className='grid grid-cols-2'>
//                     <Controller
//                       name='selectDate'
//                       control={methods.control}
//                       render={({ field, fieldState }) => (
//                         <div>
//                           <DateInput2
//                             mode='single'
//                             selectedDate={
//                               selectedDate
//                                 ? new Date(selectedDate).toISOString()
//                                 : undefined
//                             }
//                             onDateChange={setSelectedDate}
//                             // disabled={disabledDays}
//                             disabledDays={disabledDays}
//                             disabled={isSinglePickerDisabled}
//                           />
//                           {fieldState.error && (
//                             <p className='text-red-500'>
//                               {fieldState.error.message}
//                             </p>
//                           )}
//                         </div>
//                       )}
//                     />
//                     <TimeInput
//                       name='startTime'
//                       label='Start Time'
//                       disabled={isSinglePickerDisabled}
//                     />
//                     <TimeInput
//                       name='breakTime'
//                       label='Break Time'
//                       disabled={isSinglePickerDisabled}
//                     />
//                     <TimeInput
//                       name='endTime'
//                       label='End Time'
//                       disabled={isSinglePickerDisabled}
//                     />
//                   </div>
//                 </div>

//                 <button className='mt-1 w-max justify-center rounded-lg bg-green-400  px-2 py-2 text-sm font-bold uppercase text-[#0d0303] transition-colors hover:bg-green-300'>
//                   + Add New Schedule
//                 </button>
//                 <button className='mt-4 h-12 w-48 justify-center rounded-lg bg-yellow-400 text-lg font-bold uppercase text-[#0d0303] transition-colors hover:bg-yellow-300'>
//                   Add New Board
//                 </button>
//               </form>
//             </FormProvider>
//           </div>
//         </div>
//       </div>
//     </div>
//   )
// }
// export default ScheduleForm

// import React, { useState } from 'react'
// import { useForm, FormProvider, Controller } from 'react-hook-form'
// import { zodResolver } from '@hookform/resolvers/zod'
// import { z } from 'zod'
// // import { IconX } from '@tabler/icons-react'

// import DateInput2 from '@/components/ui/date'
// import { addDays, format } from 'date-fns'
// import { UserNav } from '@/components/user-nav'
// import TimeInput from '@/components/ui/timepicker'

// const schema = z.object({})

// // Define the form values type based on the schema
// type FormValues = z.infer<typeof schema>

// const ScheduleForm: React.FC = () => {
//   const methods = useForm<FormValues>({
//     resolver: zodResolver(schema),
//   })

//   const [selectedDate, setSelectedDate] = useState<string | undefined>(
//     undefined
//   )
//   const initialRange = {
//     from: '',
//     to: '',
//   }

//   const [selectedRange, setSelectedRange] = useState<
//     { from: string | undefined; to: string | undefined } | undefined
//   >(initialRange)

//   const [schedules, setSchedules] = useState<
//     {
//       id: number
//       date?: string
//       startTime?: string
//       breakTime?: string
//       endTime?: string
//     }[]
//   >([])

//   const disabledDays =
//     selectedRange?.from && selectedRange?.to
//       ? {
//           before: new Date(selectedRange.from),
//           after: new Date(selectedRange.to),
//         }
//       : { before: new Date() }

//   const isSinglePickerDisabled = !(selectedRange?.from && selectedRange?.to)

//   const addNewSchedule = () => {
//     setSchedules([
//       ...schedules,
//       {
//         id: schedules.length,
//         date: '',
//         startTime: '',
//         breakTime: '',
//         endTime: '',
//       },
//     ])
//   }

//   const removeSchedule = (id: number) => {
//     setSchedules(schedules.filter((schedule) => schedule.id !== id))
//   }

//   // const handleScheduleChange = (
//   //   id: number,
//   //   field: 'date' | 'startTime' | 'breakTime' | 'endTime',
//   //   value: string
//   // ) => {
//   //   setSchedules((prev) =>
//   //     prev.map((schedule) =>
//   //       schedule.id === id ? { ...schedule, [field]: value } : schedule
//   //     )
//   //   )
//   // }

//   const handleScheduleChange = (
//     id: number,
//     field: 'date' | 'startTime' | 'breakTime' | 'endTime',
//     value: string
//   ) => {
//     setSchedules((prev) =>
//       prev.map((schedule) =>
//         schedule.id === id
//           ? {
//               ...schedule,
//               [field]:
//                 field === 'date'
//                   ? value
//                   : `${schedule.date?.slice(0, 10)}T${value}:00.000Z`,
//             }
//           : schedule
//       )
//     )
//   }

//   // const onSubmit = () => {
//   //   console.log(schedules)
//   //   // console.log(data)
//   // }
//   const onSubmit = async () => {
//     const payload: {
//       employerId: string
//       departmentId: string
//       startDate: string
//       endDate: string
//       employeeSchedules: { [key: string]: any[] } // Provide an index signature
//     } = {
//       employerId: 'employer_id_value',
//       departmentId: 'department_id_value',
//       startDate: '2024-06-10T07:00:00.000Z',
//       endDate: '2024-06-10T07:00:00.000Z',
//       employeeSchedules: {},
//     }
//     // Populate employee schedules
//     schedules.forEach((schedule) => {
//       if (schedule.date) {
//         const dateKey = new Date(schedule.date).toISOString() // Convert to ISO string format
//         if (!payload.employeeSchedules[dateKey]) {
//           payload.employeeSchedules[dateKey] = [] // Initialize array if not already present
//         }
//         // Push employee schedule object
//         payload.employeeSchedules[dateKey].push({
//           employeeId: 'employee_id_value', // Replace with actual value from form state or loop variable
//           startTime: schedule.startTime, // Assuming startTime, breakTime, and endTime are already in ISO string format
//           breakTime: schedule.breakTime,
//           endTime: schedule.endTime,
//         })
//       }
//     })
//     console.log(payload)
//     setSelectedRange(undefined)
//     setSchedules([])
//   }
//   return (
//     <div className='container relative flex h-auto w-full flex-col'>
//       <div className='flex-1 space-y-10  px-4 py-6 md:px-8'>
//         <div className='flex items-center justify-between space-y-2'>
//           <h1 className='text-2xl font-bold tracking-tight md:text-3xl'>
//             Add new Task Board
//           </h1>
//           <UserNav color='black' />
//         </div>
//         <div className='grid grid-cols-1 '>
//           <div className='rounded-md bg-gray-50 p-10'>
//             <FormProvider {...methods}>
//               <form
//                 onSubmit={methods.handleSubmit(onSubmit)}
//                 className='grid gap-3'
//               >
//                 <div>
//                   <label htmlFor='dateRange'>Schedule Date Range</label>
//                   <Controller
//                     name='dateRange'
//                     control={methods.control}
//                     render={({ field, fieldState }) => (
//                       <div>
//                         <DateInput2
//                           mode='range'
//                           selectedRange={
//                             selectedRange
//                               ? {
//                                   from: selectedRange.from
//                                     ? new Date(selectedRange.from).toISOString()
//                                     : undefined,
//                                   to: selectedRange.to
//                                     ? new Date(selectedRange.to).toISOString()
//                                     : undefined,
//                                 }
//                               : undefined
//                           }
//                           onRangeChange={setSelectedRange}
//                           disabledDays={disabledDays}
//                         />
//                         {fieldState.error && (
//                           <p className='text-red-500'>
//                             {fieldState.error.message}
//                           </p>
//                         )}
//                       </div>
//                     )}
//                   />
//                 </div>
//                 <div className='grid grid-cols-3 gap-4 '>
//                   {schedules.map((schedule) => (
//                     <div key={schedule.id} className='bg-white p-2'>
//                       <label htmlFor={`selectDate-${schedule.id}`}>
//                         {schedule.date ? (
//                           <p className='text-semibold py-2 text-xl'>
//                             Selected Date :{' '}
//                             {format(new Date(schedule.date), 'MMMM dd, yyyy')}
//                           </p>
//                         ) : (
//                           <p>Select Date</p>
//                         )}
//                       </label>
//                       <div className='grid grid-cols-2 items-center gap-2'>
//                         <Controller
//                           name={`selectDate-${schedule.id}`}
//                           control={methods.control}
//                           render={({ field, fieldState }) => (
//                             <div>
//                               <DateInput2
//                                 mode='single'
//                                 selectedDate={
//                                   schedule.date
//                                     ? new Date(schedule.date).toISOString()
//                                     : undefined
//                                 }
//                                 onDateChange={(date) =>
//                                   handleScheduleChange(
//                                     schedule.id,
//                                     'date',
//                                     date || ''
//                                   )
//                                 }
//                                 disabledDays={disabledDays}
//                                 disabled={isSinglePickerDisabled}
//                               />
//                               {fieldState.error && (
//                                 <p className='text-red-500'>
//                                   {fieldState.error.message}
//                                 </p>
//                               )}
//                             </div>
//                           )}
//                         />

//                         <TimeInput
//                           name={`startTime-${schedule.id}`}
//                           label='Start Time'
//                           disabled={isSinglePickerDisabled}
//                           onChange={(time) =>
//                             handleScheduleChange(schedule.id, 'startTime', time)
//                           }
//                         />
//                         <TimeInput
//                           name={`breakTime-${schedule.id}`}
//                           label='Break Time'
//                           disabled={isSinglePickerDisabled}
//                           onChange={(time) =>
//                             handleScheduleChange(schedule.id, 'breakTime', time)
//                           }
//                         />
//                         <TimeInput
//                           name={`endTime-${schedule.id}`}
//                           label='End Time'
//                           disabled={isSinglePickerDisabled}
//                           onChange={(time) =>
//                             handleScheduleChange(schedule.id, 'endTime', time)
//                           }
//                         />
//                       </div>
//                       <button
//                         type='button'
//                         onClick={() => removeSchedule(schedule.id)}
//                         className='mt-2 w-max justify-center rounded-lg bg-red-400 px-2 py-1 text-sm font-bold uppercase text-white transition-colors hover:bg-red-300'
//                       >
//                         Remove
//                       </button>
//                     </div>
//                   ))}
//                 </div>
//                 <button
//                   type='button'
//                   onClick={addNewSchedule}
//                   className='mt-1 w-max justify-center rounded-lg bg-green-400 px-2 py-2 text-sm font-bold uppercase text-[#0d0303] transition-colors hover:bg-green-300'
//                 >
//                   + Add New Schedule
//                 </button>
//                 <button className='mt-4 h-12 w-48 justify-center rounded-lg bg-yellow-400 text-lg font-bold uppercase text-[#0d0303] transition-colors hover:bg-yellow-300'>
//                   Add All Schedules
//                 </button>
//               </form>
//             </FormProvider>
//           </div>
//         </div>
//       </div>
//     </div>
//   )
// }

// export default ScheduleForm

// import React, { useState } from 'react'
// import { useForm, FormProvider, Controller } from 'react-hook-form'
// import { zodResolver } from '@hookform/resolvers/zod'
// import { z } from 'zod'
// // import { IconX } from '@tabler/icons-react'

// import DateInput2 from '@/components/ui/date'
// import { addDays, format } from 'date-fns'
// import { UserNav } from '@/components/user-nav'
// import TimeInput from '@/components/ui/timepicker'

// const schema = z.object({})

// // Define the form values type based on the schema
// type FormValues = z.infer<typeof schema>

// const ScheduleForm: React.FC = () => {
//   const methods = useForm<FormValues>({
//     resolver: zodResolver(schema),
//   })

//   const [selectedDate, setSelectedDate] = useState<string | undefined>(
//     undefined
//   )
//   const initialRange = {
//     from: '',
//     to: '',
//   }

//   const [selectedRange, setSelectedRange] = useState<
//     { from: string | undefined; to: string | undefined } | undefined
//   >(initialRange)

//   const [schedules, setSchedules] = useState<
//     {
//       id: number
//       date?: string
//       startTime?: string
//       breakTime?: string
//       endTime?: string
//     }[]
//   >([])

//   const onSubmit = (data: FormValues) => {
//     console.log(data)
//   }

//   const disabledDays =
//     selectedRange?.from && selectedRange?.to
//       ? {
//           before: new Date(selectedRange.from),
//           after: new Date(selectedRange.to),
//         }
//       : { before: new Date() }

//   const isSinglePickerDisabled = !(selectedRange?.from && selectedRange?.to)

//   const addNewSchedule = () => {
//     setSchedules([
//       ...schedules,
//       {
//         id: schedules.length,
//         date: '',
//         startTime: '',
//         breakTime: '',
//         endTime: '',
//       },
//     ])
//   }

//   const handleScheduleChange = (
//     id: number,
//     field: 'date' | 'startTime' | 'breakTime' | 'endTime',
//     value: string
//   ) => {
//     setSchedules((prev) =>
//       prev.map((schedule) =>
//         schedule.id === id ? { ...schedule, [field]: value } : schedule
//       )
//     )
//   }

//   return (
//     <div className='container relative mb-10 flex h-auto w-full flex-col'>
//       <div className='flex-1 space-y-10 overflow-hidden px-4 py-6 md:px-8'>
//         <div className='flex items-center justify-between space-y-2'>
//           <h1 className='text-2xl font-bold tracking-tight md:text-3xl'>
//             Add new Task Board
//           </h1>
//           <UserNav color='black' />
//         </div>
//         <div className='grid grid-cols-2 '>
//           <div className='rounded-md bg-gray-50 p-10'>
//             <FormProvider {...methods}>
//               <form
//                 onSubmit={methods.handleSubmit(onSubmit)}
//                 className='grid gap-3'
//               >
//                 <div>
//                   <label htmlFor='dateRange'>Schedule Date Range</label>
//                   <Controller
//                     name='dateRange'
//                     control={methods.control}
//                     render={({ field, fieldState }) => (
//                       <div>
//                         <DateInput2
//                           mode='range'
//                           selectedRange={
//                             selectedRange
//                               ? {
//                                   from: selectedRange.from
//                                     ? new Date(selectedRange.from).toISOString()
//                                     : undefined,
//                                   to: selectedRange.to
//                                     ? new Date(selectedRange.to).toISOString()
//                                     : undefined,
//                                 }
//                               : undefined
//                           }
//                           onRangeChange={setSelectedRange}
//                           disabledDays={disabledDays}
//                         />
//                         {fieldState.error && (
//                           <p className='text-red-500'>
//                             {fieldState.error.message}
//                           </p>
//                         )}
//                       </div>
//                     )}
//                   />
//                 </div>
//                 {schedules.map((schedule) => (
//                   <div key={schedule.id} className='bg-red-100 p-2'>
//                     <label htmlFor={`selectDate-${schedule.id}`}>
//                       Select Date{' '}
//                     </label>
//                     <div className='grid grid-cols-2'>
//                       <Controller
//                         name={`selectDate-${schedule.id}`}
//                         control={methods.control}
//                         render={({ field, fieldState }) => (
//                           <div>
//                             <DateInput2
//                               mode='single'
//                               selectedDate={
//                                 schedule.date
//                                   ? new Date(schedule.date).toISOString()
//                                   : undefined
//                               }
//                               onDateChange={(date) =>
//                                 handleScheduleChange(
//                                   schedule.id,
//                                   'date',
//                                   date || ''
//                                 )
//                               }
//                               disabledDays={disabledDays}
//                               disabled={isSinglePickerDisabled}
//                             />
//                             {fieldState.error && (
//                               <p className='text-red-500'>
//                                 {fieldState.error.message}
//                               </p>
//                             )}
//                           </div>
//                         )}
//                       />
//                       <TimeInput
//                         name={`startTime-${schedule.id}`}
//                         label='Start Time'
//                         disabled={isSinglePickerDisabled}
//                         onChange={(time) =>
//                           handleScheduleChange(schedule.id, 'startTime', time)
//                         }
//                       />
//                       <TimeInput
//                         name={`breakTime-${schedule.id}`}
//                         label='Break Time'
//                         disabled={isSinglePickerDisabled}
//                         onChange={(time) =>
//                           handleScheduleChange(schedule.id, 'breakTime', time)
//                         }
//                       />
//                       <TimeInput
//                         name={`endTime-${schedule.id}`}
//                         label='End Time'
//                         disabled={isSinglePickerDisabled}
//                         onChange={(time) =>
//                           handleScheduleChange(schedule.id, 'endTime', time)
//                         }
//                       />
//                     </div>
//                   </div>
//                 ))}
//                 <button
//                   type='button'
//                   onClick={addNewSchedule}
//                   className='mt-1 w-max justify-center rounded-lg bg-green-400 px-2 py-2 text-sm font-bold uppercase text-[#0d0303] transition-colors hover:bg-green-300'
//                 >
//                   + Add New Schedule
//                 </button>
//                 <button className='mt-4 h-12 w-48 justify-center rounded-lg bg-yellow-400 text-lg font-bold uppercase text-[#0d0303] transition-colors hover:bg-yellow-300'>
//                   Add New Board
//                 </button>
//               </form>
//             </FormProvider>
//           </div>
//         </div>
//       </div>
//     </div>
//   )
// }

// export default ScheduleForm
