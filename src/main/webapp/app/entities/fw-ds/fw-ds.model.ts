import * as dayjs from 'dayjs';

export interface IFwDs {
  id?: number;
  hoteltime?: dayjs.Dayjs | null;
  rq?: dayjs.Dayjs | null;
  xz?: number | null;
  memo?: string | null;
  fwy?: string | null;
  roomn?: string | null;
  rtype?: string | null;
  empn?: string | null;
  sl?: number | null;
}

export class FwDs implements IFwDs {
  constructor(
    public id?: number,
    public hoteltime?: dayjs.Dayjs | null,
    public rq?: dayjs.Dayjs | null,
    public xz?: number | null,
    public memo?: string | null,
    public fwy?: string | null,
    public roomn?: string | null,
    public rtype?: string | null,
    public empn?: string | null,
    public sl?: number | null
  ) {}
}

export function getFwDsIdentifier(fwDs: IFwDs): number | undefined {
  return fwDs.id;
}
