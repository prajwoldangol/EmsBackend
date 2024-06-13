import { UserNav } from '@/components/user-nav'
import { useForm, FormProvider, Controller } from 'react-hook-form'
import { zodResolver } from '@hookform/resolvers/zod'
import { z } from 'zod'
import { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { AppDispatch, RootState } from '@/store/store'
import { DepartmentDto } from '@/data/userData'
import { AddDepartment, EditDepartment } from '@/store/slice/userSlice'
import { useNavigate, useSearchParams } from 'react-router-dom'
const schema = z.object({
  name: z.string().min(2, { message: 'Name Is Required' }),
})

// Define the form values type based on the schema
type FormValues = z.infer<typeof schema>

const AddEmployeeForm: React.FC = () => {
  const methods = useForm<FormValues>({
    resolver: zodResolver(schema),
  })
  const navigate = useNavigate()
  const [searchId] = useSearchParams()
  const deptId: string | null = searchId.get('did')
  const dispatch = useDispatch<AppDispatch>()
  const user = useSelector((state: RootState) => state.users)
  // const [registrationError, setRegistrationError] = useState<string>('')
  const [initialName, setInitialName] = useState<string | undefined>(undefined)
  const [loading, setLoading] = useState<boolean>(true)
  useEffect(() => {
    const dataArray = user.userData.emsEmployerDeptDtoList
    const foundObject = dataArray.find((item) => item.id === deptId)

    if (foundObject) {
      setInitialName(foundObject.name)
    } else {
      setInitialName(undefined) // Optionally set a default or handle case when no match is found
    }
    setLoading(false)
    console.log(foundObject)
  }, [user])
  const onSubmit = async (data: FormValues) => {
    const deptData: DepartmentDto = {
      employerId: user.userId,
      token: user.token,
      name: data.name,
      id: deptId,
    }
    const result =
      initialName === undefined
        ? await dispatch(AddDepartment(deptData))
        : await dispatch(EditDepartment(deptData))
    methods.reset()
    if (result.payload && result.payload.name) {
      setInitialName(result.payload.name)
    }
    navigate('/dashboard/allDepartments')
    console.log(result)
  }

  if (loading) {
    return <p>Loading...</p> // Optionally, show a loading indicator
  }

  return (
    <div className='container relative flex h-auto w-full flex-col'>
      <div className=' flex-1 space-y-10 overflow-hidden px-4 py-6 md:px-8'>
        <div className='flex items-center justify-between space-y-2'>
          <h1 className='text-2xl font-bold tracking-tight md:text-3xl'>
            {initialName === undefined
              ? `Add New Department or Team`
              : `Edit Department or Team`}
          </h1>
          <UserNav color='black' />
        </div>
        <div className='grid grid-cols-2 '>
          <div className='rounded-md bg-gray-50 p-10 shadow-[rgba(17,_17,_26,_0.1)_0px_0px_16px]'>
            <FormProvider {...methods}>
              {/* {registrationError && (
                <p className='text-red-500'>{registrationError}</p>
              )} */}
              <form
                onSubmit={methods.handleSubmit(onSubmit)}
                className='grid gap-3'
              >
                <div>
                  <label htmlFor='name'>Name</label>
                  <Controller
                    name='name'
                    control={methods.control}
                    defaultValue={initialName}
                    render={({ field, fieldState }) => (
                      <div>
                        <input
                          id='name'
                          {...field}
                          className='flex h-9 w-full rounded-md border border-input bg-transparent px-3 py-1 text-sm shadow-sm transition-colors file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-1 focus-visible:ring-ring disabled:cursor-not-allowed disabled:opacity-50'
                          value={field.value || ''}
                          // onChange={(e) => {
                          //   field.onChange(e)
                          //   // setInitialName(e.target.value)
                          // }}
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

                <button className='mt-4 w-1/2 justify-center rounded-lg bg-yellow-300  py-2 text-lg font-bold uppercase text-gray-900 transition-colors hover:bg-yellow-500'>
                  {initialName === undefined
                    ? `Add New Department`
                    : `Update Department`}
                </button>
              </form>
            </FormProvider>
          </div>
        </div>
      </div>
    </div>
  )
}
export default AddEmployeeForm
