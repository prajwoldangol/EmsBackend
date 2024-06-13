import { ReactNode } from 'react'

interface BoxWrapProps {
  children: ReactNode
  css: string
}
const BoxWrap = ({ children, css }: BoxWrapProps) => {
  return (
    <div
      className={`shadow-[rgba(17,17,26,0.1) 0px 0px 16px] flex flex-1 items-center rounded-sm border border-gray-200 bg-white p-4 ${css} `}
    >
      {children}
    </div>
  )
}

export default BoxWrap
