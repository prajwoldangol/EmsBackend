import HomeNavBar from '../nav/nav-bar'
import { ForgotPasswordForm } from './components/forgot-form'

export default function ForgotPassword() {
  return (
    <>
      <HomeNavBar />
      <div className='container grid  flex-col items-center justify-center lg:max-w-none lg:px-0'>
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
            <h1 className='text-xl font-medium'>Reset Password</h1>
          </div>
          {/* <Card className='bg-white bg-opacity-75 p-6'> */}
          <div className='bg-white bg-opacity-75 p-6 '>
            <ForgotPasswordForm />
          </div>
          {/* </Card> */}
        </div>
      </div>
    </>
  )
}
