import * as dayjs from 'dayjs';

export interface ICzlCz {
  id?: number;
  tjrq?: dayjs.Dayjs;
  typeid?: number | null;
  type?: string;
  fjsl?: number | null;
  kfl?: number | null;
  pjz?: number | null;
  ysfz?: number | null;
  sjfz?: number | null;
  fzcz?: number | null;
  pjzcj?: number | null;
  kfsM?: number | null;
  kflM?: number | null;
  pjzM?: number | null;
  fzsr?: number | null;
  dayz?: number | null;
  hoteltime?: dayjs.Dayjs | null;
  empn?: string | null;
  monthz?: number | null;
  hoteldm?: string | null;
  isnew?: number | null;
}

export class CzlCz implements ICzlCz {
  constructor(
    public id?: number,
    public tjrq?: dayjs.Dayjs,
    public typeid?: number | null,
    public type?: string,
    public fjsl?: number | null,
    public kfl?: number | null,
    public pjz?: number | null,
    public ysfz?: number | null,
    public sjfz?: number | null,
    public fzcz?: number | null,
    public pjzcj?: number | null,
    public kfsM?: number | null,
    public kflM?: number | null,
    public pjzM?: number | null,
    public fzsr?: number | null,
    public dayz?: number | null,
    public hoteltime?: dayjs.Dayjs | null,
    public empn?: string | null,
    public monthz?: number | null,
    public hoteldm?: string | null,
    public isnew?: number | null
  ) {}
}

export function getCzlCzIdentifier(czlCz: ICzlCz): number | undefined {
  return czlCz.id;
}
