import { UserNav } from '@/components/user-nav'

import DashboardBox from './components/dashboard-box'
import DashboardTable from './components/dashboard-table'
import DashboardCard from './components/dashboard-card'

export default function Dashboard() {
  return (
    <div className='container relative flex h-auto w-full flex-col'>
      {/* <div className='ml-auto mt-5 flex items-center space-x-4'>
        <UserNav />
      </div> */}

      {/* ===== Main ===== */}
      <div className=' flex-1 space-y-10 overflow-hidden px-4 py-6 md:px-8'>
        <div className='flex items-center justify-between space-y-2'>
          <h1 className='text-2xl font-bold tracking-tight md:text-3xl'>
            Welcome to Dashboard
          </h1>
          <UserNav color='black' />
        </div>
        <DashboardBox />
        <div className='flex w-full flex-row flex-wrap gap-3'>
          <DashboardTable />
          <DashboardCard />
          <DashboardCard />
        </div>
        {/* <div className='mb-4 grid grid-cols-1 gap-6 xl:grid-cols-3'>
          <div className='relative flex flex-col overflow-hidden rounded-xl bg-white bg-clip-border text-gray-700 shadow-md xl:col-span-2'>
            <div className='relative m-0 flex items-center justify-between overflow-hidden rounded-xl bg-transparent bg-clip-border p-6 text-gray-700 shadow-none'>
              <div>
                <h6 className='text-blue-gray-900 mb-1 block font-sans text-base font-semibold leading-relaxed tracking-normal antialiased'>
                  Projects
                </h6>
                <p className='text-blue-gray-600 flex items-center gap-1 font-sans text-sm font-normal leading-normal antialiased'>
                  <svg
                    xmlns='http://www.w3.org/2000/svg'
                    fill='none'
                    viewBox='0 0 24 24'
                    stroke-width='3'
                    stroke='currentColor'
                    aria-hidden='true'
                    className='h-4 w-4 text-blue-500'
                  >
                    <path
                      stroke-linecap='round'
                      stroke-linejoin='round'
                      d='M4.5 12.75l6 6 9-13.5'
                    ></path>
                  </svg>
                  <strong>30 done</strong> this month
                </p>
              </div>
              <button
                aria-expanded='false'
                aria-haspopup='menu'
                id=':r5:'
                className='middle none text-blue-gray-500 hover:bg-blue-gray-500/10 active:bg-blue-gray-500/30 relative h-8 max-h-[32px] w-8 max-w-[32px] rounded-lg text-center font-sans text-xs font-medium uppercase transition-all disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none'
                type='button'
                aria-label='Open menu'
              >
                <span className='absolute left-1/2 top-1/2 -translate-x-1/2 -translate-y-1/2 transform'>
                  <svg
                    xmlns='http://www.w3.org/2000/svg'
                    fill='currenColor'
                    viewBox='0 0 24 24'
                    stroke-width='3'
                    stroke='currentColor'
                    aria-hidden='true'
                    className='h-6 w-6'
                  >
                    <path
                      stroke-linecap='round'
                      stroke-linejoin='round'
                      d='M12 6.75a.75.75 0 110-1.5.75.75 0 010 1.5zM12 12.75a.75.75 0 110-1.5.75.75 0 010 1.5zM12 18.75a.75.75 0 110-1.5.75.75 0 010 1.5z'
                    ></path>
                  </svg>
                </span>
              </button>
            </div>
            <div className='overflow-x-scroll p-6 px-0 pb-2 pt-0'>
              <table className='w-full min-w-[640px] table-auto'>
                <thead>
                  <tr>
                    <th className='border-blue-gray-50 border-b px-6 py-3 text-left'>
                      <p className='text-blue-gray-400 block font-sans text-[11px] font-medium uppercase antialiased'>
                        companies
                      </p>
                    </th>
                    <th className='border-blue-gray-50 border-b px-6 py-3 text-left'>
                      <p className='text-blue-gray-400 block font-sans text-[11px] font-medium uppercase antialiased'>
                        budget
                      </p>
                    </th>
                    <th className='border-blue-gray-50 border-b px-6 py-3 text-left'>
                      <p className='text-blue-gray-400 block font-sans text-[11px] font-medium uppercase antialiased'>
                        completion
                      </p>
                    </th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td className='border-blue-gray-50 border-b px-5 py-3'>
                      <div className='flex items-center gap-4'>
                        <p className='text-blue-gray-900 block font-sans text-sm font-bold leading-normal antialiased'>
                          Material XD Version
                        </p>
                      </div>
                    </td>

                    <td className='border-blue-gray-50 border-b px-5 py-3'>
                      <p className='text-blue-gray-600 block font-sans text-xs font-medium antialiased'>
                        $14,000
                      </p>
                    </td>
                    <td className='border-blue-gray-50 border-b px-5 py-3'>
                      <div className='w-10/12'></div>
                    </td>
                    <td>
                      <a
                        href='#'
                        className='middle none text-blue-gray-500 hover:bg-blue-gray-500/10 active:bg-blue-gray-500/30 relative mr-4 h-8 max-h-[32px] w-8 max-w-[32px] rounded-lg text-center font-sans text-xs font-medium uppercase transition-all disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none'
                      >
                        <span className='absolute left-1/2 top-1/2 -translate-x-1/2 -translate-y-1/2 transform'>
                          <svg
                            xmlns='http://www.w3.org/2000/svg'
                            fill='currenColor'
                            viewBox='0 0 24 24'
                            stroke-width='3'
                            stroke='currentColor'
                            aria-hidden='true'
                            className='h-6 w-6'
                          >
                            <path
                              stroke-linecap='round'
                              stroke-linejoin='round'
                              d='M12 6.75a.75.75 0 110-1.5.75.75 0 010 1.5zM12 12.75a.75.75 0 110-1.5.75.75 0 010 1.5zM12 18.75a.75.75 0 110-1.5.75.75 0 010 1.5z'
                            ></path>
                          </svg>
                        </span>
                      </a>
                    </td>
                  </tr>
                  <tr>
                    <td className='border-blue-gray-50 border-b px-5 py-3'>
                      <div className='flex items-center gap-4'>
                        <p className='text-blue-gray-900 block font-sans text-sm font-bold leading-normal antialiased'>
                          Add Progress Track
                        </p>
                      </div>
                    </td>
                    <td className='border-blue-gray-50 border-b px-5 py-3'>
                      <p className='text-blue-gray-600 block font-sans text-xs font-medium antialiased'>
                        $3,000
                      </p>
                    </td>
                    <td className='border-blue-gray-50 border-b px-5 py-3'>
                      <div className='w-10/12'>
                        <p className='text-blue-gray-600 mb-1 block font-sans text-xs font-medium antialiased'>
                          10%
                        </p>
                        <div className='flex-start bg-blue-gray-50 flex h-1 w-full overflow-hidden rounded-sm font-sans text-xs font-medium'>
                          <div
                            className='flex h-full items-center justify-center bg-gradient-to-tr from-blue-600 to-blue-400 text-white'
                            style={{ width: '10%' }}
                          ></div>
                        </div>
                      </div>
                    </td>
                  </tr>
                  <tr>
                    <td className='border-blue-gray-50 border-b px-5 py-3'>
                      <div className='flex items-center gap-4'>
                        <p className='text-blue-gray-900 block font-sans text-sm font-bold leading-normal antialiased'>
                          Fix Platform Errors
                        </p>
                      </div>
                    </td>
                    <td className='border-blue-gray-50 border-b px-5 py-3'>
                      <p className='text-blue-gray-600 block font-sans text-xs font-medium antialiased'>
                        Not set
                      </p>
                    </td>
                    <td className='border-blue-gray-50 border-b px-5 py-3'>
                      <div className='w-10/12'>
                        <p className='text-blue-gray-600 mb-1 block font-sans text-xs font-medium antialiased'>
                          100%
                        </p>
                        <div className='flex-start bg-blue-gray-50 flex h-1 w-full overflow-hidden rounded-sm font-sans text-xs font-medium'>
                          <div
                            className='flex h-full items-center justify-center bg-gradient-to-tr from-green-600 to-green-400 text-white'
                            style={{ width: '100%' }}
                          ></div>
                        </div>
                      </div>
                    </td>
                  </tr>
                  <tr>
                    <td className='border-blue-gray-50 border-b px-5 py-3'>
                      <div className='flex items-center gap-4'>
                        <p className='text-blue-gray-900 block font-sans text-sm font-bold leading-normal antialiased'>
                          Launch our Mobile App
                        </p>
                      </div>
                    </td>
                    <td className='border-blue-gray-50 border-b px-5 py-3'>
                      <p className='text-blue-gray-600 block font-sans text-xs font-medium antialiased'>
                        $20,500
                      </p>
                    </td>
                    <td className='border-blue-gray-50 border-b px-5 py-3'>
                      <div className='w-10/12'>
                        <p className='text-blue-gray-600 mb-1 block font-sans text-xs font-medium antialiased'>
                          100%
                        </p>
                        <div className='flex-start bg-blue-gray-50 flex h-1 w-full overflow-hidden rounded-sm font-sans text-xs font-medium'>
                          <div
                            className='flex h-full items-center justify-center bg-gradient-to-tr from-green-600 to-green-400 text-white'
                            style={{ width: '100%' }}
                          ></div>
                        </div>
                      </div>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div> */}
        {/* <div classNameName='grid gap-4 sm:grid-cols-2 lg:grid-cols-5'>
          <Card>
            <CardHeader className='flex flex-row items-center justify-between space-y-0 pb-2'>
              <CardTitle className='font-large text-lg'>
                Total Employees
              </CardTitle>
              <svg
                xmlns='http://www.w3.org/2000/svg'
                viewBox='0 0 24 24'
                fill='none'
                stroke='currentColor'
                strokeLinecap='round'
                strokeLinejoin='round'
                strokeWidth='2'
                className='h-4 w-4 text-muted-foreground'
              >
                <path d='M16 21v-2a4 4 0 0 0-4-4H6a4 4 0 0 0-4 4v2' />
                <circle cx='9' cy='7' r='4' />
                <path d='M22 21v-2a4 4 0 0 0-3-3.87M16 3.13a4 4 0 0 1 0 7.75' />
              </svg>
            </CardHeader>
            <CardContent className='pt-3'>
              <div className='text-5xl font-bold text-blue-600'>105</div>
            </CardContent>
          </Card>
          <Card>
            <CardHeader className='flex flex-row items-center justify-between space-y-0 pb-2'>
              <CardTitle className='font-large text-lg'>
                On Leave or Absent
              </CardTitle>
              <svg
                xmlns='http://www.w3.org/2000/svg'
                viewBox='0 0 24 24'
                fill='none'
                stroke='currentColor'
                strokeLinecap='round'
                strokeLinejoin='round'
                strokeWidth='2'
                className='h-4 w-4 text-muted-foreground'
              >
                <rect width='20' height='14' x='2' y='5' rx='2' />
                <path d='M2 10h20' />
              </svg>
            </CardHeader>
            <CardContent className='pt-3'>
              <div className='text-5xl font-bold text-red-400'>3</div>
            </CardContent>
          </Card>
          <Card>
            <CardHeader className='flex flex-row items-center justify-between space-y-0 pb-2'>
              <CardTitle className='font-large text-lg'>Off Duty</CardTitle>
              <svg
                xmlns='http://www.w3.org/2000/svg'
                viewBox='0 0 24 24'
                fill='none'
                stroke='currentColor'
                strokeLinecap='round'
                strokeLinejoin='round'
                strokeWidth='2'
                className='h-4 w-4 text-muted-foreground'
              >
                <rect width='20' height='14' x='2' y='5' rx='2' />
                <path d='M2 10h20' />
              </svg>
            </CardHeader>
            <CardContent className='pt-3'>
              <div className='text-5xl font-bold text-red-600'>10</div>
            </CardContent>
          </Card>

          <Card>
            <CardHeader className='flex flex-row items-center justify-between space-y-0 pb-2'>
              <CardTitle className='font-large text-lg'>Active Now</CardTitle>
              <svg
                xmlns='http://www.w3.org/2000/svg'
                viewBox='0 0 24 24'
                fill='none'
                stroke='currentColor'
                strokeLinecap='round'
                strokeLinejoin='round'
                strokeWidth='2'
                className='h-4 w-4 text-muted-foreground'
              >
                <path d='M22 12h-4l-3 9L9 3l-3 9H2' />
              </svg>
            </CardHeader>
            <CardContent className='pt-3'>
              <div className='text-5xl font-bold text-green-600'>70</div>
            </CardContent>
          </Card>
        </div>
        <div className='grid grid-cols-1 gap-4 lg:grid-cols-7'>
          <Card className='col-span-1 lg:col-span-2'>
            <CardHeader>
              <CardTitle>Recent Activity</CardTitle>
            </CardHeader>
            <CardContent className='pl-2'>
              <EmployeeActivity />
            </CardContent>
          </Card>
          <Card className='col-span-1 lg:col-span-2'>
            <CardHeader>
              <CardTitle>Time Card History</CardTitle>
            </CardHeader>
            <CardContent className='pl-2'>
              <EmployeeActivity />
            </CardContent>
          </Card>

          <Card className='col-span-1 lg:col-span-3'>
            <CardHeader>
              <CardTitle>Employees On Duty</CardTitle>
            </CardHeader>
            <CardContent>
              <RecentSales />
            </CardContent>
          </Card>
        </div> */}
      </div>
    </div>
  )
}
