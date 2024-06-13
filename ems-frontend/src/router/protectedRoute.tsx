import { employeeState } from '@/store/slice/employeeSlice'
import { userState } from '@/store/slice/userSlice'
import { RootState } from '@/store/store'
import { ReactNode } from 'react'
import { useSelector } from 'react-redux'
import { Navigate, useLocation } from 'react-router-dom'

interface ProtectedPageProps {
  children: ReactNode
  allowedRoles?: string[]
}
const ProtectedPage = ({ children, allowedRoles }: ProtectedPageProps) => {
  const currentUser = useSelector<RootState, userState>((state) => state.users)
  const currentEmployee = useSelector<RootState, employeeState>(
    (state) => state.employee
  )
  const location = useLocation()


  // Determine the user type based on the route
  const isEmployeeRoute = location.pathname.startsWith('/employee-dashboard')
  const isAuthenticated = isEmployeeRoute
    ? currentEmployee.loggedIn
    : currentUser.loggedIn
  const userRole = isEmployeeRoute ? currentEmployee.role : currentUser.role

  // Redirect if not authenticated
  if (!isAuthenticated) {
    const loginPath = isEmployeeRoute ? '/employee-login' : '/login'
    return (
      <Navigate to={loginPath} state={{ alert: 'You need to log in first' }} />
    )
  }
  // if (!currentUser.loggedIn || !currentEmployee.loggedIn) {
  //   let loginPath = '/'
  //   if (location.pathname.startsWith('/employee-dashboard')) {
  //     loginPath = '/employee-login'
  //   } else if (location.pathname.startsWith('/dashboard')) {
  //     loginPath = '/login'
  //   }
  //   return (
  //     <Navigate to={loginPath} state={{ alert: 'You need to log in first' }} />
  //   )
  // }
  if (allowedRoles && !allowedRoles.includes(userRole)) {
    // Redirect to unauthorized page if user's role is not in the allowed roles
    return (
      <Navigate
        to='/unauthorized'
        state={{ alert: 'You are not authorized to access this page' }}
      />
    )
  }
  return children
}

export default ProtectedPage
