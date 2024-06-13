import { LogoutActions, LogoutUser } from '@/store/slice/userSlice'
import { AppDispatch } from '@/store/store'
import { IconBellRinging } from '@tabler/icons-react'
import React, { useEffect, useRef, useState } from 'react'
import { useDispatch } from 'react-redux'

export function UserNav({ color }: { color: string }) {
  const [open, setOpen] = useState(false)
  const [opennotification, setOpennotification] = useState(false)
  const dropdownRef = useRef<HTMLDivElement>(null)
  const notificationRef = useRef<HTMLDivElement>(null)
  const toggleDropDown = () => {
    setOpen(!open)
  }
  const toggleNotification = () => {
    setOpennotification(!opennotification)
  }
  const handleClickOutside = (event: MouseEvent) => {
    if (
      dropdownRef.current &&
      !dropdownRef.current.contains(event.target as Node)
    ) {
      setOpen(false)
    }
    if (
      notificationRef.current &&
      !notificationRef.current.contains(event.target as Node)
    ) {
      setOpennotification(false)
    }
  }
  const dispatch = useDispatch<AppDispatch>()
  useEffect(() => {
    document.body.addEventListener('click', handleClickOutside)

    return () => {
      document.body.removeEventListener('click', handleClickOutside)
    }
  }, [])
  const logoutDispatch = async (e: MouseEvent) => {
    e.preventDefault()
    console.log('click')
    dispatch(LogoutUser())
  }
  return (
    <div className='relative flex flex-wrap items-center justify-center gap-6'>
      <div
        className='relative cursor-pointer'
        onClick={toggleNotification}
        ref={notificationRef}
      >
        <IconBellRinging size={35} color={color} />
        <span
          className={`${
            opennotification ? 'hidden' : 'block'
          } absolute -top-2 left-4 scale-75 rounded-full bg-red-500 p-0.5 px-2 text-sm text-red-50`}
        >
          4
        </span>
        <div
          className={`${
            opennotification ? 'block' : 'hidden'
          } absolute right-0 top-full z-20 w-max overflow-hidden rounded-md border bg-white transition-all duration-500 ease-in-out`}
          style={{
            // maxHeight: opennotification ? '200px' : '0',
            opacity: opennotification ? 1 : 0,
          }}
        >
          <div className='relative'>
            <div className='absolute right-4 top-0 -mt-2 h-0 w-0 rotate-45 transform border-l-2 border-r-2 border-t-2 border-[#1F2937]' />
            <a
              href='#'
              className='block px-4 py-2 text-lg text-gray-900 hover:text-yellow-900'
            >
              Somebody just viewed your profile.
            </a>
            <a
              href='#'
              className='block px-4 py-2 text-lg text-gray-900 hover:text-yellow-900'
            >
              Setting
            </a>
            <a
              href='#'
              className='block px-4 py-2 text-lg  text-gray-900 hover:text-yellow-900'
            >
              Logout
            </a>
          </div>
        </div>
      </div>

      <div
        className='flex items-center'
        onClick={toggleDropDown}
        ref={dropdownRef}
      >
        <p className='flex h-10 w-10 cursor-pointer items-center justify-center rounded-full bg-yellow-400 text-gray-900'>
          SM
        </p>
      </div>
      <div
        className={`${
          open ? 'block' : 'hidden'
        } absolute right-0 top-full z-20 w-40 overflow-hidden rounded-md border bg-white transition-all duration-500 ease-in-out`}
        style={{ maxHeight: open ? '200px' : '0', opacity: open ? 1 : 0 }}
      >
        <div className='relative'>
          <div className='absolute right-4 top-0 -mt-2 h-0 w-0 rotate-45 transform border-l-2 border-r-2 border-t-2 border-[#1F2937]' />
          <a
            href='#'
            className='block px-4 py-2 text-lg text-gray-900 hover:text-yellow-900'
          >
            Profile
          </a>
          <a
            href='#'
            className='block px-4 py-2 text-lg text-gray-900 hover:text-yellow-900'
          >
            Setting
          </a>
          <a
            href='#'
            onClick={logoutDispatch}
            className='block px-4 py-2 text-lg  text-gray-900 hover:text-yellow-900'
          >
            Logout
          </a>
        </div>
      </div>
    </div>
  )
}
