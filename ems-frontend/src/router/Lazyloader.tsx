import { lazy, ReactNode, Suspense } from 'react'
import ProtectedPage from './protectedRoute'
import FullscreenLoader from '@/components/ui/loader'

const loadComponent = (
  importFunc: () => Promise<{ default: React.ComponentType<any> }>
) => {
  const LazyComponent = lazy(importFunc)
  return (
    <Suspense fallback={<FullscreenLoader />}>
      <LazyComponent />
    </Suspense>
  )
}

interface ProtectedRouteProps {
  componentImport: () => Promise<{ default: React.ComponentType<any> }>
  requiredRole?: string[]
}

const ProtectedRoute = ({
  componentImport,
  requiredRole,
}: ProtectedRouteProps) => {
  return (
    <ProtectedPage allowedRoles={requiredRole}>
      {loadComponent(componentImport)}
    </ProtectedPage>
  )
}
export default ProtectedRoute
