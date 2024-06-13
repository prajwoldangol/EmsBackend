import React, { useState } from 'react'
import { useForm, FormProvider, Controller } from 'react-hook-form'
import { zodResolver } from '@hookform/resolvers/zod'
import { z } from 'zod'
// import { IconX } from '@tabler/icons-react'

import DateInput2 from '@/components/ui/date'
import { addDays, format } from 'date-fns'
import { UserNav } from '@/components/user-nav'
import TimeInput from '@/components/ui/timepicker'
import { useDispatch, useSelector } from 'react-redux'
import { AppDispatch, RootState } from '@/store/store'
import { AddSchedule } from '@/store/slice/scheduleSlice'

// const schema = z.object({})
const schema = z.object({
  // dateRange: z.object({
  //   from: z.string().min(1, { message: 'From date is required' }),
  //   to: z.string().min(1, { message: 'To date is required' }),
  // }),
})

// // Define the form values type based on the schema
type FormValues = z.infer<typeof schema>

const ScheduleForm: React.FC = () => {
  const methods = useForm<FormValues>({
    resolver: zodResolver(schema),
    defaultValues: {
      dateRange: {
        from: '',
        to: '',
      },
    },
  })

  const initialRange = {
    from: '',
    to: '',
  }

  const [selectedRange, setSelectedRange] = useState<
    { from: string | undefined; to: string | undefined } | undefined
  >(initialRange)

  const [schedules, setSchedules] = useState<
    {
      id: number
      employee: string
      date?: string
      startTime?: string
      breakTime?: string
      endTime?: string
    }[]
  >([])

  const disabledDays =
    selectedRange?.from && selectedRange?.to
      ? {
          before: new Date(selectedRange.from),
          after: new Date(selectedRange.to),
        }
      : { before: new Date() }

  const isSinglePickerDisabled = !(selectedRange?.from && selectedRange?.to)

  const addNewSchedule = () => {
    setSchedules([
      ...schedules,
      {
        id: schedules.length,
        employee: '',
        date: '',
        startTime: '',
        breakTime: '',
        endTime: '',
      },
    ])
  }

  const removeSchedule = (id: number) => {
    setSchedules(schedules.filter((schedule) => schedule.id !== id))
  }

  const handleScheduleChange = (
    id: number,
    field: 'employee' | 'date' | 'startTime' | 'breakTime' | 'endTime',
    value: string
  ) => {
    setSchedules((prev) =>
      prev.map((schedule) =>
        schedule.id === id
          ? {
              ...schedule,
              [field]:
                field === 'date' || field === 'employee'
                  ? value
                  : `${schedule.date?.slice(0, 10)}T${value}:00.000Z`,
            }
          : schedule
      )
    )
  }
  const employees = useSelector((state: RootState) => state.users)
  const dispatch = useDispatch<AppDispatch>()
  const onSubmit = async (data: FormData) => {
    if (!selectedRange || !selectedRange.from || !selectedRange.to) {
      alert('Select Date Ranges')
      return
    }
    console.log(selectedRange)
    if (schedules.length < 1) {
      alert('Add Schedules data')
      return
    }
    const payload: {
      employerId: string
      departmentId: string
      startDate: string
      endDate: string
      employeeSchedules: { [key: string]: unknown[] } // Provide an index signature
    } = {
      employerId: employees.userId,
      departmentId: '',
      startDate: selectedRange?.from,
      endDate: selectedRange?.to,
      employeeSchedules: {},
    }
    console.log(schedules)
    // Populate employee schedules
    schedules.forEach((schedule) => {
      if (schedule.date) {
        const dateKey = new Date(schedule.date).toISOString() // Convert to ISO string format
        if (!payload.employeeSchedules[dateKey]) {
          payload.employeeSchedules[dateKey] = [] // Initialize array if not already present
        }
        // Push employee schedule object
        payload.employeeSchedules[dateKey].push({
          employeeId: schedule.employee, // Replace with actual value from form state or loop variable
          startTime: schedule.startTime, // Assuming startTime, breakTime, and endTime are already in ISO string format
          breakTime: schedule.breakTime,
          endTime: schedule.endTime,
        })
      }
    })
    // console.log(selectedRange)
    // console.log(payload)
    const returndata = dispatch(AddSchedule(payload))
    returndata
      .then((result) => {
        console.log(result)
      })
      .catch((error) => {
        console.error(error)
      })
    setSelectedRange(undefined)
    setSchedules([])
  }
  return (
    <div className='container relative flex h-auto w-full flex-col'>
      <div className='flex-1 space-y-10  px-4 py-6 md:px-8'>
        <div className='flex items-center justify-between space-y-2'>
          <h1 className='text-2xl font-bold tracking-tight md:text-3xl'>
            Add new Task Board
          </h1>
          <UserNav color='black' />
        </div>
        <div className='grid grid-cols-1 '>
          <div className='rounded-md bg-gray-50 p-10'>
            <FormProvider {...methods}>
              <form
                onSubmit={methods.handleSubmit(onSubmit)}
                className='grid gap-3'
              >
                <div>
                  <label htmlFor='dateRange'>Schedule Date Range</label>
                  <Controller
                    name='dateRange'
                    control={methods.control}
                    render={({ field, fieldState }) => (
                      <div>
                        <DateInput2
                          {...field}
                          mode='range'
                          selectedRange={
                            selectedRange
                              ? {
                                  from: selectedRange.from
                                    ? new Date(selectedRange.from).toISOString()
                                    : undefined,
                                  to: selectedRange.to
                                    ? new Date(selectedRange.to).toISOString()
                                    : undefined,
                                }
                              : undefined
                          }
                          onRangeChange={setSelectedRange}
                          // onRangeChange={(range) => {
                          //   setSelectedRange(range)
                          //   field.onChange(range) // Ensure the form field value is updated
                          // }}
                          disabledDays={disabledDays}
                        />
                        {fieldState.error && (
                          <p className='text-red-500'>
                            {fieldState.error.message}
                          </p>
                        )}
                      </div>
                    )}
                  />
                </div>
                <div className='grid grid-cols-3 gap-4 '>
                  {schedules.map((schedule) => (
                    <div key={schedule.id} className='bg-white p-2'>
                      {employees.userData.emsEmployerEmpDtos.length > 0 && (
                        <div>
                          <label htmlFor={`employee-${schedule.id}`}>
                            Employee
                          </label>
                          <Controller
                            name={`employee-${schedule.id}` as keyof FormValues}
                            control={methods.control}
                            render={({ field, fieldState }) => (
                              <div>
                                <select
                                  id={`employee-${schedule.id}`}
                                  {...field}
                                  className='flex h-9 w-full rounded-md border border-input bg-transparent px-3 py-1 text-sm shadow-sm transition-colors file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-1 focus-visible:ring-ring disabled:cursor-not-allowed disabled:opacity-50'
                                  // value={field.value || ''}
                                  onChange={(employee) =>
                                    handleScheduleChange(
                                      schedule.id,
                                      'employee',
                                      employee.target.value
                                    )
                                  }
                                >
                                  <option value=''>No Employee</option>
                                  {employees.userData.emsEmployerEmpDtos.map(
                                    (emp) => (
                                      <option key={emp.id} value={emp.id}>
                                        {emp.firstName + ' ' + emp.lastName}
                                      </option>
                                    )
                                  )}
                                </select>
                              </div>
                            )}
                          />
                        </div>
                      )}

                      <label htmlFor={`selectDate-${schedule.id}`}>
                        {schedule.date ? (
                          <p className='text-semibold py-2 text-xl'>
                            Selected Date :{' '}
                            {format(new Date(schedule.date), 'MMMM dd, yyyy')}
                          </p>
                        ) : (
                          <p className='pt-2'>Select Date</p>
                        )}
                      </label>

                      <div className='grid grid-cols-2 items-center gap-2'>
                        <Controller
                          name={`selectDate-${schedule.id}` as keyof FormValues}
                          control={methods.control}
                          render={({ field, fieldState }) => (
                            <div>
                              <DateInput2
                                {...field}
                                mode='single'
                                selectedDate={
                                  schedule.date
                                    ? new Date(schedule.date).toISOString()
                                    : undefined
                                }
                                onDateChange={(date) =>
                                  handleScheduleChange(
                                    schedule.id,
                                    'date',
                                    date || ''
                                  )
                                }
                                disabledDays={disabledDays}
                                disabled={isSinglePickerDisabled}
                              />
                              {fieldState.error && (
                                <p className='text-red-500'>
                                  {fieldState.error.message}
                                </p>
                              )}
                            </div>
                          )}
                        />

                        <TimeInput
                          name={`startTime-${schedule.id}`}
                          label='Start Time'
                          disabled={isSinglePickerDisabled}
                          onChange={(time) =>
                            handleScheduleChange(schedule.id, 'startTime', time)
                          }
                        />
                        <TimeInput
                          name={`breakTime-${schedule.id}`}
                          label='Break Time'
                          disabled={isSinglePickerDisabled}
                          onChange={(time) =>
                            handleScheduleChange(schedule.id, 'breakTime', time)
                          }
                        />
                        <TimeInput
                          name={`endTime-${schedule.id}`}
                          label='End Time'
                          disabled={isSinglePickerDisabled}
                          onChange={(time) =>
                            handleScheduleChange(schedule.id, 'endTime', time)
                          }
                        />
                      </div>
                      <button
                        type='button'
                        onClick={() => removeSchedule(schedule.id)}
                        className='mt-2 w-max justify-center rounded-lg bg-red-400 px-2 py-1 text-sm font-bold uppercase text-white transition-colors hover:bg-red-300'
                      >
                        Remove
                      </button>
                    </div>
                  ))}
                </div>
                <button
                  type='button'
                  onClick={addNewSchedule}
                  className='mt-1 w-max justify-center rounded-lg bg-green-400 px-2 py-2 text-sm font-bold uppercase text-[#0d0303] transition-colors hover:bg-green-300'
                >
                  + Add New Schedule
                </button>
                <button className='mt-4 h-12 w-48 justify-center rounded-lg bg-yellow-400 text-lg font-bold uppercase text-[#0d0303] transition-colors hover:bg-yellow-300'>
                  Add All Schedules
                </button>
              </form>
            </FormProvider>
          </div>
        </div>
      </div>
    </div>
  )
}

export default ScheduleForm
