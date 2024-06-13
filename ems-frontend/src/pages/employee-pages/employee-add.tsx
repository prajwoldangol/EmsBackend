import { UserNav } from '@/components/user-nav'
import { useForm, FormProvider, Controller } from 'react-hook-form'
import { zodResolver } from '@hookform/resolvers/zod'
import { z } from 'zod'
import { useState } from 'react'
import { EmployeeSignupDto } from '@/data/userData'
import { AppDispatch, RootState } from '@/store/store'
import { useDispatch, useSelector } from 'react-redux'
import { AddEmployee } from '@/store/slice/userSlice'
const schema = z.object({
  email: z
    .string()
    .min(4, { message: 'Email Is Required' })
    .email({ message: 'Enter Valid Email' }),
  phone: z.string().regex(/^\d{10}$/, {
    message: 'Phone number must be exactly 10 digits',
  }),
  firstName: z.string().min(2, { message: 'First Name is Required' }),
  lastName: z.string().min(2, { message: 'Last Name is Required' }),
  department: z.string().optional(),
})

// Define the form values type based on the schema
type FormValues = z.infer<typeof schema>

const EmployeeAdd: React.FC = () => {
  const methods = useForm<FormValues>({
    resolver: zodResolver(schema),
  })
  const dispatch = useDispatch<AppDispatch>()
  const user = useSelector((state: RootState) => state.users)
  const department = user.userData.emsEmployerDeptDtoList
  const [registrationError, setRegistrationError] = useState<string>('')
  const onSubmit = async (data: FormValues) => {
    const employeeData: EmployeeSignupDto = {
      username: data.email,
      firstName: data.firstName,
      lastName: data.lastName,
      phone: data.phone,
      token: user.token,
      employerId: user.userId,
      departmentId: data.department,
    }
    const result = await dispatch(AddEmployee(employeeData))
    if (!result.payload.error) {
      setRegistrationError('')
      methods.reset()
    } else {
      setRegistrationError(
        'Something went Wrong !! Remember email and phone should be unique'
      )
    }

    console.log(result)
  }
  return (
    <div className='container relative flex h-auto w-full flex-col'>
      <div className=' flex-1 space-y-10 overflow-hidden px-4 py-6 md:px-8'>
        <div className='flex items-center justify-between space-y-2'>
          <h1 className='text-2xl font-bold tracking-tight md:text-3xl'>
            Add New Employees
          </h1>
          <UserNav color='black' />
        </div>
        <div className='grid grid-cols-2 '>
          <div className='rounded-md bg-gray-50 p-10 shadow-[rgba(17,_17,_26,_0.1)_0px_0px_16px]'>
            <FormProvider {...methods}>
              {registrationError && (
                <p className='text-red-500'>{registrationError}</p>
              )}
              <form
                onSubmit={methods.handleSubmit(onSubmit)}
                className='grid gap-3'
              >
                <div>
                  <label htmlFor='firstName'>First Name</label>
                  <Controller
                    name='firstName'
                    control={methods.control}
                    render={({ field, fieldState }) => (
                      <div>
                        <input
                          id='firstName'
                          {...field}
                          className='flex h-9 w-full rounded-md border border-input bg-transparent px-3 py-1 text-sm shadow-sm transition-colors file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-1 focus-visible:ring-ring disabled:cursor-not-allowed disabled:opacity-50'
                          value={field.value || ''}
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
                <div>
                  <label htmlFor='lastName'>Last Name</label>
                  <Controller
                    name='lastName'
                    control={methods.control}
                    render={({ field, fieldState }) => (
                      <div>
                        <input
                          id='lastName'
                          {...field}
                          className='flex h-9 w-full rounded-md border border-input bg-transparent px-3 py-1 text-sm shadow-sm transition-colors file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-1 focus-visible:ring-ring disabled:cursor-not-allowed disabled:opacity-50'
                          value={field.value || ''}
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
                <div>
                  <label htmlFor='email'>Email</label>
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
                          <p className='text-red-500'>
                            {fieldState.error.message}
                          </p>
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
                          <p className='text-red-500'>
                            {fieldState.error.message}
                          </p>
                        )}
                      </div>
                    )}
                  />
                </div>
                {department.length > 0 && (
                  <div>
                    <label htmlFor='department'>Department</label>
                    <Controller
                      name='department'
                      control={methods.control}
                      render={({ field, fieldState }) => (
                        <div>
                          <select
                            id='department'
                            {...field}
                            className='flex h-9 w-full rounded-md border border-input bg-transparent px-3 py-1 text-sm shadow-sm transition-colors file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-1 focus-visible:ring-ring disabled:cursor-not-allowed disabled:opacity-50'
                            value={field.value || ''}
                          >
                            <option value=''>No Department</option>
                            {department.map((dept) => (
                              <option key={dept.id} value={dept.id}>
                                {dept.name}
                              </option>
                            ))}
                          </select>
                          {fieldState.error && (
                            <p className='text-red-500'>
                              {fieldState.error.message}
                            </p>
                          )}
                        </div>
                      )}
                    />
                  </div>
                )}

                <button className='mt-4 w-1/2 justify-center rounded-lg bg-yellow-300  py-2 text-lg font-bold uppercase text-gray-900 transition-colors hover:bg-yellow-500'>
                  Add New Employee
                </button>
              </form>
            </FormProvider>
          </div>
        </div>
      </div>
    </div>
  )
}
export default EmployeeAdd
