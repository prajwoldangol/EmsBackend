import BoxWrap from '@/components/ui/boxwrapper'
import React from 'react'
import { Link } from 'react-router-dom'

const DashboardBox = () => {
  return (
    <div className='flex flex-wrap gap-4'>
      <Link to='/dashboard/addEmployee'>
        <BoxWrap css='bg-pink-500 cursor-pointer'>
          <div className=' '>
            <span className='text-xl font-semibold text-white'>
              Add New Employee
            </span>
          </div>
        </BoxWrap>
      </Link>
      <Link to='/dashboard/allEmployees'>
        <BoxWrap css='bg-yellow-300 cursor-pointer'>
          <div className=' '>
            <span className='text-xl font-semibold text-gray-900'>
              View All Employee
            </span>
          </div>
        </BoxWrap>
      </Link>
      <Link to='/dashboard/tasks'>
        <BoxWrap css='bg-orange-400 cursor-pointer'>
          <div className=' '>
            <span className='text-xl font-semibold text-white'>
              Add Task Board
            </span>
          </div>
        </BoxWrap>
      </Link>
      <Link to='/dashboard/schedule'>
        <BoxWrap css='bg-indigo-500 cursor-pointer'>
          <div className=' '>
            <span className='text-xl font-semibold text-gray-900'>
              Add New Schedule
            </span>
          </div>
        </BoxWrap>
      </Link>
    </div>
  )
}

export default DashboardBox
