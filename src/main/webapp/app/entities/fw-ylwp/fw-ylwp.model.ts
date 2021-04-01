import * as dayjs from 'dayjs';

export interface IFwYlwp {
  id?: number;
  roomn?: string | null;
  guestname?: string | null;
  memo?: string | null;
  sdr?: string | null;
  sdrq?: dayjs.Dayjs | null;
  rlr?: string | null;
  rlrq?: dayjs.Dayjs | null;
  remark?: string | null;
  empn?: string | null;
  czrq?: dayjs.Dayjs | null;
  flag?: string | null;
}

export class FwYlwp implements IFwYlwp {
  constructor(
    public id?: number,
    public roomn?: string | null,
    public guestname?: string | null,
    public memo?: string | null,
    public sdr?: string | null,
    public sdrq?: dayjs.Dayjs | null,
    public rlr?: string | null,
    public rlrq?: dayjs.Dayjs | null,
    public remark?: string | null,
    public empn?: string | null,
    public czrq?: dayjs.Dayjs | null,
    public flag?: string | null
  ) {}
}

export function getFwYlwpIdentifier(fwYlwp: IFwYlwp): number | undefined {
  return fwYlwp.id;
}
