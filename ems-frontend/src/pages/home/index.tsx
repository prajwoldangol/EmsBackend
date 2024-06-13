import { useEffect, useRef, useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { IconCheck, IconX, IconCalendarStats } from '@tabler/icons-react'
// import bgImage from '@/assets/bg.jpg'
import logo from '@/assets/logo.png'
import banner from '@/assets/banner.png'
import { useDispatch, useSelector } from 'react-redux'
import { AppDispatch, RootState } from '@/store/store'
import { StripePayDto } from '@/data/userData'
import { StripePay } from '@/store/slice/userSlice'
import FullscreenLoader from '@/components/ui/loader'
// import { Button } from '@/components/custom/button'
export default function HomePage() {
  //   const bgStyle = {
  //     background: 'linear-gradient(to right, #91eae4, #86a8e7, #7f7fd5)',
  //   }
  const sectionRef = useRef<HTMLTableSectionElement>(null)
  const [loading, setLoading] = useState<boolean>(false)
  // const scrollToSection = () => {
  //   if (sectionRef.current) {
  //     sectionRef.current.scrollIntoView({ behavior: 'smooth' })
  //   }
  // }
  useEffect(() => {
    // Apply styles to the body tag
    document.body.style.background =
      'linear-gradient(to right, #91eae4, #86a8e7, #7f7fd5)'
    document.body.style.background = `radial-gradient(
        circle,
        rgba(243, 229, 252, 1) 0%,
        rgba(229, 243, 252, 1) 100%
      )`
  }, [])
  useEffect(() => {
    if (location.hash === '#plan') {
      const element = document.getElementById('plan')
      if (element) {
        element.scrollIntoView({ behavior: 'smooth' })
      }
    }
  }, [location])
  const navigate = useNavigate()
  const dispatch = useDispatch<AppDispatch>()
  const currentUser = useSelector((state: RootState) => state.users)
  const onClickSubmit = async (e: MouseEvent<HTMLButtonElement>) => {
    setLoading(true)
    const credentials: StripePayDto = {
      userId: currentUser.userId,
      priceId: e?.target?.value,
      username: currentUser.loggedInUser,
    }
    const user = await dispatch(StripePay(credentials))
    // if (user.payload.error) {
    // }
    if (user.payload && !user.payload.error) {
      // Check if URL exists in the payload
      window.location.href = user.payload // Redirect using window.location.href
      return
    }

    navigate('/login')
  }
  // const [sessionDetails, setSessionDetails] = useState(null)

  // useEffect(() => {
  //   const jwt =
  //     'eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiVVNFUiIsInVzZXJuYW1lIjoicmFtQGdtYWlsLmNvbSIsInN1YiI6InJhbUBnbWFpbC5jb20iLCJpYXQiOjE3MTc3ODU0NDksImV4cCI6MTcxNzc5MTU2OX0.eHWJcc4HkT78nu_HjzMKJKMEVo8AlyXFozrxVdgHq1k'
  //   fetch(`http://localhost:9090/user/employer/ODghoFQyTK1f8gKs1S8ssDQ`, {
  //     method: 'GET',
  //     headers: {
  //       Authorization: `Bearer ${jwt}`,
  //       'Content-Type': 'application/json',
  //     },
  //   })
  //     .then((response) => {
  //       return response.json()
  //     })
  //     .then((data) => setSessionDetails(data))
  //     .catch((error) => console.error('Error fetching session details:', error))
  // }, [])
  // console.log(sessionDetails)
  return (
    <main
      id='content'
      //   className={`container mx-auto p-32`}
      //   style={bgStyle}
    >
      {loading && <FullscreenLoader />}
      <header className=' bg-blue-50 bg-opacity-25 '>
        <nav className='container flex  items-center gap-10 px-4 py-3'>
          <picture className=' h-12 w-12'>
            <img src={logo} alt='logo' />
          </picture>
          {/* <div className='block sm:hidden'>
          <button className='flex items-center rounded border border-gray-400 px-3 py-2 text-gray-300 hover:border-white hover:text-white'>
            <svg
              className='h-3 w-3 fill-current'
              viewBox='0 0 20 20'
              xmlns='http://www.w3.org/2000/svg'
            >
              <title>Menu</title>
              <path d='M0 3h20v2H0V3zm0 6h20v2H0V9zm0 6h20v2H0v-2z' />
            </svg>
          </button>
        </div> */}
          <ul className=' flex flex-wrap gap-10 py-2 font-semibold text-gray-900'>
            <li className='py-2 text-xl tracking-wide transition duration-200 hover:text-blue-700'>
              <Link to='/'>Home</Link>
            </li>
            <li className='py-2 text-xl tracking-wide transition duration-200 hover:text-blue-700'>
              <Link to='/dashboard'>Dashboard</Link>
            </li>
            <li className='py-2 text-xl tracking-wide transition duration-200 hover:text-blue-700'>
              <Link to='/employee-dashboard'>Employee Portal</Link>
            </li>
          </ul>
          <ul className='ml-auto flex flex-wrap gap-8'>
            <li className='cursor-pointer rounded border border-black bg-transparent px-8 py-2 text-xl font-bold text-black transition duration-300  hover:bg-black hover:text-white'>
              <Link to='/login'>Login</Link>
            </li>
            <li className='cursor-pointer rounded bg-blue-500 px-8 py-2  text-xl font-bold text-white transition duration-300 hover:bg-blue-400'>
              <Link to='/register'>Register</Link>
            </li>
          </ul>
        </nav>
      </header>
      <section
        className='gap-15 relative flex h-screen h-screen  bg-opacity-70 bg-cover bg-fixed'
        // style={{
        //   backgroundImage: `linear-gradient(rgba(0, 0, 0, 0.6), rgba(0, 0, 0, 0.75)), url(${bgImage})`,
        // }}
      >
        <div className='container flex gap-9'>
          <div className='mt-24 font-bold text-white'>
            <h1
              // className='bg-gradient-to-r from-blue-600 via-green-500 to-indigo-400 bg-clip-text text-8xl text-transparent '
              // style={{
              //   background: `linear-gradient(to right, #ff0000, #00ff00)`,
              // }}
              className='text-8xl text-black'
            >
              Take Control
            </h1>
            <h2 className='text-7xl leading-snug text-black'>
              {' '}
              Of Everyday Work
            </h2>
            <ul className='text-2xl font-extralight text-black'>
              <li className='my-2 flex items-center gap-3'>
                <IconCalendarStats size={25} color='green' />
                Simplify scheduling, time clocks, and payroll all in one app.
              </li>
              <li className='my-2 flex items-center gap-3'>
                <IconCheck size={25} color='green' stroke={5} />
                Control labor costs and simplify timesheets.
              </li>
              <li className='my-2 flex items-center gap-3'>
                <IconCheck size={25} color='green' stroke={5} />
                Employee Onboarding.
              </li>
            </ul>
            {/* <a
              onClick={() => {
                sectionRef.current?.scrollIntoView({ behavior: 'smooth' })
              }}
              // variant='outline'
              // size='lg'
              className='mt-6 bg-black font-semibold text-white hover:border-gray-600 hover:bg-transparent hover:text-black'
            >
              Get Started
            </a> */}
          </div>
          <picture className='sticky top-36 hidden h-fit max-w-xl md:block'>
            <img src={banner} alt='banner' />
          </picture>
        </div>
      </section>
      <section className='bg-[#ae80ff24]' id='plan'>
        <div className={`container mx-auto  py-24`}>
          <h2 className='mb-16 text-center text-5xl font-semibold'>
            Available Plans
          </h2>
          {/* <div className='flex flex-col flex-wrap gap-10 px-12 sm:flex-row'> */}
          <div
            className='grid grid-cols-1 gap-10 px-12 md:grid-cols-2 lg:grid-cols-3'
            ref={sectionRef}
          >
            <div className='rounded-xl bg-white bg-opacity-30 p-10 shadow-xl'>
              <h5 className='mb-4 text-2xl font-medium text-gray-500'>
                Basic plan
              </h5>
              <p className='pb-4'>
                Get started and Simplify the process of tracking & managing
                shifts.
              </p>
              <div className='item-baseline flex text-gray-900'>
                <span className='text-3xl font-semibold'>$</span>
                <span className='text-7xl font-extrabold'>10</span>
                <span className='ml-1 text-xl font-normal text-gray-500'>
                  /month
                </span>
              </div>
              <ul className='my-7 space-y-5'>
                <li className='flex space-x-3'>
                  <IconCheck size={18} color='green' />
                  <span className='leading-tight text-gray-600'>2 team</span>
                </li>
                <li className='flex space-x-3'>
                  <IconCheck size={18} color='green' />
                  <span className='leading-tight text-gray-600'>
                    20 cloud team
                  </span>
                </li>
                <li className='flex space-x-3'>
                  <IconCheck size={18} color='green' />
                  <span className='leading-tight text-gray-600'>20 team</span>
                </li>
                <li className='flex space-x-3'>
                  <IconCheck size={18} color='green' />
                  <span className='leading-tight text-gray-600'>2 team</span>
                </li>

                <li className='flex space-x-3 line-through decoration-gray-500'>
                  <IconX size={18} color='red' />
                  <span className='leading-tight text-gray-600'>2 team</span>
                </li>
                <li className='flex space-x-3 line-through decoration-gray-500'>
                  <IconX size={18} color='red' />
                  <span className='leading-tight text-gray-600'>2 team</span>
                </li>
                <li className='flex space-x-3 line-through decoration-gray-500'>
                  <IconX size={18} color='red' />
                  <span className='leading-tight text-gray-600'>2 team</span>
                </li>
              </ul>
              <button
                onClick={onClickSubmit}
                value='price_1PNf3GIljL5sgmrY5RuKzvWQ'
                className='transtion-all w-full rounded-lg border border-green-600 bg-green-600 p-2 text-lg text-white duration-300  hover:bg-green-700'
              >
                Choose Basic
              </button>
            </div>
            <div className='rounded-xl bg-stone-950  bg-opacity-90 p-10 text-white shadow-xl'>
              {/* <div className='absolute right-0 top-0 h-16 w-16'>
              <div className='absolute right-[-35px] top-[32px] w-[170px] rotate-45 transform bg-green-600 py-1 text-center font-semibold text-white'>
                Recommended
              </div>
            </div> */}
              <h5 className='text-white-500 mb-4 text-2xl font-medium'>
                Standard plan
              </h5>
              <p className='pb-4'>
                Boost your team's productivity and improve communication.
              </p>
              <div className='item-baseline flex text-blue-100'>
                <span className='text-3xl font-semibold'>$</span>
                <span className='text-7xl font-extrabold'>20</span>
                <span className='text-white-500 ml-1 text-xl font-normal'>
                  /month
                </span>
              </div>
              <ul className='my-7 space-y-5'>
                <li className='flex space-x-3'>
                  <IconCheck size={18} color='white' />
                  <span className='text-white-600 leading-tight'>2 team</span>
                </li>
                <li className='flex space-x-3'>
                  <IconCheck size={18} color='white' />
                  <span className='text-white-600 leading-tight'>
                    20 cloud team
                  </span>
                </li>
                <li className='flex space-x-3'>
                  <IconCheck size={18} color='white' />
                  <span className='text-white-600 leading-tight'>20 team</span>
                </li>
                <li className='flex space-x-3'>
                  <IconCheck size={18} color='white' />
                  <span className='text-white-600 leading-tight'>2 team</span>
                </li>
                <li className='decoration-white-500 flex space-x-3 line-through'>
                  <IconX size={18} color='red' />
                  <span className='text-white-600 leading-tight'>2 team</span>
                </li>
                <li className='decoration-white-500 flex space-x-3 line-through'>
                  <IconX size={18} color='red' />
                  <span className='text-white-600 leading-tight'>2 team</span>
                </li>
                <li className='decoration-white-500 flex space-x-3 line-through'>
                  <IconX size={18} color='red' />
                  <span className='text-white-600 leading-tight'>2 team</span>
                </li>
              </ul>
              <button
                onClick={onClickSubmit}
                value='price_1PNf4eIljL5sgmrY3Q9rxBOm'
                className='transtion-all w-full rounded-lg border border-white bg-white p-2 text-lg  text-stone-950  duration-300  hover:bg-transparent hover:text-white'
              >
                Choose Standard
              </button>
            </div>
            <div className='rounded-xl bg-white bg-opacity-30 p-10 shadow-xl'>
              <h5 className='mb-4 text-2xl font-medium text-gray-500'>
                Plus plan
              </h5>
              <p className='pb-4'>
                Get control of your labor costs and streamline operations.
              </p>
              <div className='item-baseline flex text-gray-900'>
                <span className='text-3xl font-semibold'>$</span>
                <span className='text-7xl font-extrabold'>30</span>
                <span className='ml-1 text-xl font-normal text-gray-500'>
                  /month
                </span>
              </div>
              <ul className='my-7 space-y-5'>
                <li className='flex space-x-3'>
                  <IconCheck size={18} color='green' />
                  <span className='leading-tight text-gray-600'>2 team</span>
                </li>
                <li className='flex space-x-3'>
                  <IconCheck size={18} color='green' />
                  <span className='leading-tight text-gray-600'>
                    20 cloud team
                  </span>
                </li>
                <li className='flex space-x-3'>
                  <IconCheck size={18} color='green' />
                  <span className='leading-tight text-gray-600'>20 team</span>
                </li>
                <li className='flex space-x-3'>
                  <IconCheck size={18} color='green' />
                  <span className='leading-tight text-gray-600'>2 team</span>
                </li>
                <li className='flex space-x-3 line-through decoration-gray-500'>
                  <IconX size={18} color='red' />
                  <span className='leading-tight text-gray-600'>2 team</span>
                </li>
                <li className='flex space-x-3 line-through decoration-gray-500'>
                  <IconX size={18} color='red' />
                  <span className='leading-tight text-gray-600'>2 team</span>
                </li>
                <li className='flex space-x-3 line-through decoration-gray-500'>
                  <IconX size={18} color='red' />
                  <span className='leading-tight text-gray-600'>2 team</span>
                </li>
              </ul>
              <button
                onClick={onClickSubmit}
                value='price_1PNf4pIljL5sgmrYrJi5VOt6'
                className='transtion-all w-full rounded-lg border border-blue-600 bg-blue-600 p-2 text-lg text-white duration-300  hover:bg-blue-700'
              >
                Choose plus
              </button>
            </div>
          </div>
        </div>
      </section>
    </main>
  )
}
