// import { Avatar, AvatarFallback, AvatarImage } from '@/components/ui/avatar'

export function RecentSales() {
  return (
    <div className='space-y-8'>
      <div className='flex items-center'>
        <div className='ml-4 space-y-1'>
          <p className='text-sm font-medium leading-none'>Olivia Martin</p>
          <p className='text-sm text-muted-foreground'>
            olivia.martin@email.com
          </p>
        </div>
        <div className='ml-auto font-medium'>Active Since 9 am</div>
      </div>
      <div className='flex items-center'>
        <div className='ml-4 space-y-1'>
          <p className='text-sm font-medium leading-none'>Jackson Lee</p>
          <p className='text-sm text-muted-foreground'>jackson.lee@email.com</p>
        </div>
        <div className='ml-auto font-medium'>Active Since 9 am</div>
      </div>
    </div>
  )
}
