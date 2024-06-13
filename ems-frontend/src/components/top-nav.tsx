import { cn } from '@/lib/utils'
import { Link } from 'react-router-dom'

interface TopNavProps extends React.HTMLAttributes<HTMLElement> {
  links: {
    title: string
    href: string
    isActive: boolean
  }[]
}

export function TopNav({ className, links, ...props }: TopNavProps) {
  return (
    <>
      {/* phone menu user employee */}
      <div className='md:hidden'></div>

      <nav
        className={cn(
          'hidden items-center space-x-4 md:flex lg:space-x-6',
          className
        )}
        {...props}
      >
        {links.map(({ title, href, isActive }) => (
          <Link
            key={`${title}-${href}`}
            to={href}
            className={`text-xl font-medium text-white transition-colors hover:text-yellow-300`}
          >
            {title}
          </Link>
        ))}
      </nav>
    </>
  )
}
