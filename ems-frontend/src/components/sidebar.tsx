import { useState } from 'react'

import Nav from './nav'
import { cn } from '@/lib/utils'
import { sidelinks } from '@/data/sidelinks'

export default function Sidebar2({ className }: { className: string }) {
  const [navOpened, setNavOpened] = useState(false)

  return (
    <aside
      className={cn(
        `fixed left-0 right-0 top-0 z-50 w-full border-r-2 border-r-muted transition-[width] md:bottom-0 md:right-auto md:h-svh md:w-64`,
        className
      )}
    >
      {/* Overlay in mobile */}
      <div
        onClick={() => setNavOpened(false)}
        className={`absolute inset-0 transition-[opacity] delay-100 duration-700 ${navOpened ? 'h-svh opacity-50' : 'h-0 opacity-0'} w-full bg-black md:hidden`}
      />

      <div className='relative flex h-full w-full flex-col'>
        {/* Header */}
        <div className='sticky top-0 flex h-[var(--header-height)] flex-none items-center justify-between gap-4 bg-background p-4 px-4 py-3 shadow md:px-4 md:px-8'>
          <div className={`flex items-center gap-2`}>
            <div
              className={`visible flex w-auto flex-col justify-end truncate`}
            >
              <span className='font-medium'>Employer Dashboard</span>
            </div>
          </div>

          {/* Toggle Button in mobile */}
          {/* <Button
            variant='ghost'
            size='icon'
            className='md:hidden'
            aria-label='Toggle Navigation'
            aria-controls='sidebar-menu'
            aria-expanded={navOpened}
            onClick={() => setNavOpened((prev) => !prev)}
          >
            {navOpened ? <IconX /> : <IconMenu2 />}
          </Button> */}
        </div>

        {/* Navigation links */}
        <Nav
          id='sidebar-menu'
          className={`mt-4 h-full flex-1 overflow-auto ${navOpened ? 'max-h-screen' : 'max-h-0 py-0 md:max-h-screen md:py-2'}`}
          closeNav={() => setNavOpened(false)}
          links={sidelinks}
        />
      </div>
    </aside>
  )
}
