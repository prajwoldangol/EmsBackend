export interface UserData {
  statusCode: number
  employerId: string
  token: string
  refreshToken: string
  role: string
  username: string
}

export interface EmployerSignupDto {
  username: string
  password: string
  phone: string
}
export interface EmployeeSignupDto {
  username: string
  firstName: string
  lastName: string
  phone: string
  token: string
  employerId: string
  departmentId: string
}

export interface StripePayDto {
  userId: string
  priceId: string
  username: string
}
export interface DepartmentDto {
  name: string
  employerId: string
  token: string
  id: string | undefined
}
export interface userDataDto {
  id: string
  username: string
  companyName: string
  phone: string
  joinDate: Date
  role: string
  emsEmployerEmpDtos: []
  emsEmployerDeptDtoList: []
  emsSubscriptionsDtos: []
  userDetailDto: {}
}
export interface PasswordResetDto {
  newPassword: string
  empId: string | null
}
export interface ScheduleData {
  employerId: string
  departmentId: string
  startDate: string
  endDate: string
  employeeSchedules: { [key: string]: unknown[] } // Provide an index signature
}
