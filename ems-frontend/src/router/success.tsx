import { TopNav } from '@/components/top-nav'
import { UserNav } from '@/components/user-nav'
import { GetEmployer } from '@/store/slice/userSlice'
import { AppDispatch, RootState } from '@/store/store'
import { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { useNavigate, useSearchParams } from 'react-router-dom'

export default function UnAuthorized() {
  const navigate = useNavigate()
  const [searchId] = useSearchParams()
  const sessionId: string | null = searchId.get('session_id')
  const dispatch = useDispatch<AppDispatch>()
  const user = useSelector((state: RootState) => state.users)
  const loadUser = async () => {
    await dispatch(GetEmployer([user.userId, user.token]))
  }
  useEffect(() => {
    if (sessionId) {
      loadUser()
      console.log('test')
    }
  }, [sessionId])
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
          <h1 className='text-[2rem] font-bold leading-tight text-green-400'>
            Success
          </h1>
          <span className='font-medium'>Thank you for the payment</span>
          <p className='text-center text-muted-foreground'>
            Proceed to dashboard from the link below.
          </p>
          <div className='mt-6 flex gap-4'>
            <button
              className='rounded-sm bg-green-400 px-4 py-2 font-semibold text-gray-700 transition-colors hover:bg-green-200'
              onClick={() => navigate('/dashboard')}
            >
              {' '}
              Proceed to Dashboard
            </button>
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
    title: 'Dashboard',
    href: '/dashboard',
    isActive: false,
  },
]
