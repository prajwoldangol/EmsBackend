import { Link } from 'react-router-dom'
// import { buttonVariants } from './custom/button'
// import { cn } from '@/lib/utils'
import { SideLink } from '@/data/sidelinks'
interface NavProps {
  links: SideLink[]
}

export default function Nav({ links }: NavProps) {
  const renderLink = ({ ...rest }: SideLink) => {
    const key = `${rest.title}-${rest.href}`
    return <NavLink {...rest} key={key} />
  }
  return (
    <div
      className={
        'group border-b bg-background py-2 transition-[max-height,padding] duration-500 data-[collapsed=true]:py-2 md:border-none'
      }
    >
      <nav className='grid gap-1 group-[[data-collapsed=true]]:justify-center group-[[data-collapsed=true]]:px-2'>
        {links.map(renderLink)}
      </nav>
    </div>
  )
}

interface NavLinkProps extends SideLink {
  subLink?: boolean
}

function NavLink({ title, icon, href }: NavLinkProps) {
  return (
    <Link
      to={href}
      className={
        'inline-flex h-12  items-center justify-start  whitespace-nowrap text-wrap rounded-md px-6 text-xl font-medium transition-colors hover:bg-accent hover:text-accent-foreground focus-visible:outline-none focus-visible:ring-1 focus-visible:ring-ring disabled:pointer-events-none disabled:opacity-50'
      }
    >
      <div className='mr-2'>{icon}</div>
      {title}
    </Link>
  )
}
