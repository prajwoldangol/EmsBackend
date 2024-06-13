import { Link } from 'react-router-dom'

const DashboardTable = () => {
  const recentOrderData = [
    {
      department: 'Agriculture',

      id: 'Shirley A. Lape',
      schedule_date: '2022-05-17T03:24:00',
      status: 'Active',
    },
    {
      department: 'IT',
      id: 'Ryan Carroll',
      schedule_date: '2022-05-14T05:24:00',
      status: 'On Leave',
    },
    {
      department: 'IT',

      id: 'Mason Nash',
      schedule_date: '2022-05-17T07:14:00',

      status: 'Finished Shift',
    },
    {
      department: 'Sales',

      id: 'Luke Parkin',
      schedule_date: '2022-05-16T12:40:00',
      status: 'Absent',
    },
    {
      department: 'HR',

      id: 'Anthony Fry',
      schedule_date: '2022-05-14T03:24:00',
      order_total: '$876.00',
      status: 'Present',
    },
  ]
  return (
    <div className='rounded-sm border border-gray-200 bg-white px-4 pb-4 pt-3 shadow-[rgba(17,_17,_26,_0.1)_0px_0px_16px]'>
      <strong className='font-medium text-gray-700'>Today's Schedule</strong>
      <div className='mt-3 rounded-sm'>
        <table className='w-full text-gray-700'>
          <thead className='bg-gray-100 p-5'>
            <tr>
              <th
                scope='col'
                className='text-nowrap px-10 py-2.5 text-left text-sm font-normal text-gray-500 dark:text-gray-400 '
              >
                Employee Name
              </th>
              <th
                scope='col'
                className='text-nowrap px-10 py-2.5 text-left text-sm font-normal text-gray-500 dark:text-gray-400 '
              >
                Department
              </th>
              <th
                scope='col'
                className='text-nowrap px-10 py-2.5 text-left text-sm font-normal text-gray-500 dark:text-gray-400 '
              >
                Schedule
              </th>
              <th
                scope='col'
                className='text-nowrap px-10 py-2.5 text-left text-sm font-normal text-gray-500 dark:text-gray-400 '
              >
                Current Status
              </th>
            </tr>
          </thead>
          <tbody>
            {recentOrderData.map((order, index) => (
              <tr
                key={order.id}
                className={
                  index % 2 != 0 ? 'bg-gray-100 text-gray-900' : 'bg-white'
                }
              >
                <td className='text-nowrap py-2 pl-2 text-center'>
                  <Link to={`/order/${order.id}`}>{order.id}</Link>
                </td>
                <td className='text-nowrap py-2 pl-2 text-center'>
                  <Link to={`/product/${order.department}`}>
                    {order.department}
                  </Link>
                </td>

                <td className='text-nowrap py-2 pl-2 text-center'>
                  {order.schedule_date}
                </td>
                <td className='text-nowrap py-2 pl-2 text-center'>
                  {order.status}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  )
}

export default DashboardTable
