import { TopNav } from '@/components/top-nav'
import { UserNav } from '@/components/user-nav'

export default function UnAuthorized() {
  return (
    <div className='relative flex h-screen w-full flex-col'>
      {/* ===== Top Heading ===== */}
      <section className='bg-gray-800 shadow-[0_16px_30px_rgb(0,0,0,0.12)]'>
        <div className='container flex h-[var(--header-height)] flex-none items-center gap-4 bg-background bg-gray-800 p-4 shadow-[0_16px_30px_rgb(0,0,0,0.12)] md:px-8'>
          <TopNav links={topNav} />
          <div className='ml-auto flex items-center space-x-4'>
            <UserNav color='#FACC15' />
          </div>
        </div>
      </section>
      {/* ===== Main ===== */}

      <div className='h-svh'>
        <div className='m-auto flex h-full w-full flex-col items-center justify-center gap-2'>
          <h1 className='text-[4rem] font-bold leading-tight'>
            UN AUTHORIZED ACCESS
          </h1>
          <span className='font-medium'>Oops! Page Not Found!</span>
          <p className='text-center text-muted-foreground'>
            It seems like the page you're looking for <br />
            does not exist or might have been removed.
          </p>
          <div className='mt-6 flex gap-4'>
            {/* <Button variant='outline' onClick={() => navigate(-1)}>
            Go Back
          </Button>
          <Button onClick={() => navigate('/')}>Back to Home</Button> */}
          </div>
        </div>
      </div>
    </div>
  )
}

const topNav = [
  {
    title: 'Home',
    href: '/',
    isActive: true,
  },
  {
    title: 'Get Plan',
    href: '/#plan',
    isActive: false,
  },
]
