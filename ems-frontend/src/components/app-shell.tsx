import { Navigate, Outlet } from 'react-router-dom'
import Sidebar from './sidebar'
// import useIsCollapsed from '@/hooks/use-is-collapsed'

export default function AppShell() {
  // const [isCollapsed, setIsCollapsed] = useIsCollapsed()
  // const isAuthenticated = false
  // return isAuthenticated ? <Outlet /> : <Navigate to='/login' />
  // return isAuthenticated ? (
  return (
    <div className='relative h-full overflow-hidden bg-background'>
      <Sidebar className=' hidden md:block' />
      <main
        id='content'
        className={`h-screen overflow-x-hidden pt-16 transition-[margin] md:ml-64  md:pt-0`}
      >
        <Outlet />
      </main>
    </div>
  )
  // ) : (
  //   <Navigate to='/login' />
  // )
}
