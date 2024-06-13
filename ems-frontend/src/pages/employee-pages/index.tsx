import { UserNav } from '@/components/user-nav'
import DataTable from '../dashboard/components/table'

const EmployeeTable = () => {
  return (
    <div className='container relative flex h-auto w-full flex-col'>
      <div className=' flex-1 space-y-10 overflow-hidden px-4 py-6 md:px-8'>
        <div className='flex items-center justify-between space-y-2'>
          <h1 className='text-2xl font-bold tracking-tight md:text-3xl'>
            Manage Employees
          </h1>
          <UserNav color='black' />
        </div>
        <DataTable />
      </div>
    </div>
  )
}

export default EmployeeTable
