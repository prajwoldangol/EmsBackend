// import { Card } from '@/components/ui/card'
import HomeNavBar from '../nav/nav-bar'
import { Link } from 'react-router-dom'
import { SignUpForm } from './components/sign-up-form'

export default function Register() {
  return (
    <>
      <HomeNavBar />
      <div className='justify-centerlg:max-w-none container grid flex-col items-center lg:px-0'>
        <div className='mx-auto flex w-full flex-col justify-center space-y-2 sm:w-[480px] lg:p-8'>
          <div className='mb-4 flex items-center justify-center'>
            <svg
              xmlns='http://www.w3.org/2000/svg'
              viewBox='0 0 24 24'
              fill='none'
              stroke='currentColor'
              strokeWidth='2'
              strokeLinecap='round'
              strokeLinejoin='round'
              className='mr-2 h-6 w-6'
            >
              <path d='M15 6v12a3 3 0 1 0 3-3H6a3 3 0 1 0 3 3V6a3 3 0 1 0-3 3h12a3 3 0 1 0-3-3' />
            </svg>
            <h1 className='text-xl font-medium'>Register New Account</h1>
          </div>
          {/* <Card className='bg-white bg-opacity-75 p-6 '> */}
          {/* <Card className='bg-white bg-opacity-75 p-6 '> */}
          <div className='bg-white bg-opacity-75 p-6 '>
            <div className='mb-2 flex flex-col space-y-2 text-left'>
              <h1 className='text-lg font-semibold tracking-tight'>
                Create an account
              </h1>
              <p className='text-sm text-muted-foreground'>
                Enter your email and password to create an account. <br />
                Already have an account?{' '}
                <Link
                  to='/login'
                  className='underline underline-offset-4 hover:text-primary'
                >
                  Sign In
                </Link>
              </p>
            </div>
            <SignUpForm />
          </div>
        </div>
      </div>
    </>
  )
}
