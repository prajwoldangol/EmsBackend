import { useEffect, useRef } from 'react'
import { Link } from 'react-router-dom'

import logo from '@/assets/logo.png'
export default function HomeNavBar() {
  const linkRefs = useRef<(HTMLAnchorElement | null)[]>([])
  const handleClick = (index: number) => {
    if (linkRefs.current[index]) {
      linkRefs.current[index]?.click()
    }
  }

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
  return (
    <header className=' bg-blue-50 bg-opacity-25 '>
      <nav className='container flex  items-center gap-10 px-4 py-3'>
        <picture className=' h-12 w-12'>
          <img src={logo} alt='logo' />
        </picture>
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
          <li
            onClick={() => handleClick(0)}
            className='cursor-pointer rounded border border-black bg-transparent px-8 py-2 text-xl font-bold text-black transition duration-300  hover:bg-black hover:text-white'
          >
            <Link to='/login' ref={(el) => (linkRefs.current[0] = el)}>
              Login
            </Link>
          </li>
          <li
            onClick={() => handleClick(1)}
            className='cursor-pointer rounded bg-blue-500 px-8 py-2  text-xl font-bold text-white transition duration-300 hover:bg-blue-400'
          >
            <Link to='/register' ref={(el) => (linkRefs.current[1] = el)}>
              Register
            </Link>
          </li>
        </ul>
      </nav>
    </header>
  )
}
