import * as dayjs from 'dayjs';

export interface IDSpcz {
  id?: number;
  rq?: dayjs.Dayjs | null;
  czrq?: dayjs.Dayjs | null;
}

export class DSpcz implements IDSpcz {
  constructor(public id?: number, public rq?: dayjs.Dayjs | null, public czrq?: dayjs.Dayjs | null) {}
}

export function getDSpczIdentifier(dSpcz: IDSpcz): number | undefined {
  return dSpcz.id;
}
