// import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card'

// import ThemeSwitch from '@/components/theme-switch'
import { TopNav } from '@/components/top-nav'
import { UserNav } from '@/components/user-nav'
// import { useEffect } from 'react'

// import { Overview } from './components/overview'

export default function Dashboard() {
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
      <div className='container mt-5 flex-1 space-y-12 overflow-hidden px-4 py-6 md:px-8'>
        <div className='flex items-center justify-between space-y-2'>
          <h1 className='text-2xl font-bold tracking-tight md:text-3xl'>
            Welcome to Dashboard
          </h1>
        </div>
      </div>
    </div>
  )
}

const topNav = [
  {
    title: 'Overview',
    href: 'dashboard/overview',
    isActive: true,
  },
  {
    title: 'Customers',
    href: 'dashboard/customers',
    isActive: false,
  },
  {
    title: 'Products',
    href: 'dashboard/products',
    isActive: false,
  },
  {
    title: 'Settings',
    href: 'settings',
    isActive: false,
  },
]
