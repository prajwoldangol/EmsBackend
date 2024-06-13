import { cn } from '@/lib/utils'
import { Link } from 'react-router-dom'

const DashboardCard = () => {
  const popularProducts = [
    {
      id: '3432',
      employee_name: 'John Mckain"',
      product_thumbnail: 'https://placehold.jp/50x50.png',

      active_since: 'Active Since 9Am',
    },
    {
      id: '7633',
      employee_name: 'Donald Trump',
      product_thumbnail: 'https://placehold.jp/50x50.png',

      active_since: 'Active Since 11 AM',
    },
    {
      id: '6534',
      employee_name: 'Sleepy Joe',
      product_thumbnail: 'https://placehold.jp/50x50.png',
      active_since: 'On Leave Today',
    },

    {
      id: '4342',
      employee_name: 'Ron',
      product_thumbnail: 'https://placehold.jp/50x50.png',
      active_since: 'On Leave Today',
    },
  ]

  return (
    <div className='w-auto flex-1 rounded-sm border border-gray-200 bg-white p-4 shadow-[rgba(17,_17,_26,_0.1)_0px_0px_16px]'>
      <strong className='font-medium text-gray-700'>Active Employees</strong>
      <div className='mt-4 flex flex-col gap-3'>
        {popularProducts.map((product) => (
          <Link
            key={product.id}
            to={`/product/${product.id}`}
            className='flex items-start hover:no-underline'
          >
            <div className='h-10 w-10 min-w-[2.5rem] rounded-sm bg-gray-200'>
              <img
                className='h-full w-full rounded-sm object-cover'
                src={product.product_thumbnail}
                alt={product.employee_name}
              />
            </div>
            <div className='ml-4 flex-1'>
              <p className='text-sm text-gray-800'>{product.employee_name}</p>
              <span className={'text-xs font-medium text-green-500'}>
                {product.active_since}
              </span>
            </div>
          </Link>
        ))}
      </div>
    </div>
  )
}

export default DashboardCard
