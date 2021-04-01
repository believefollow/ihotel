import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DEmpnDetailComponent } from './d-empn-detail.component';

describe('Component Tests', () => {
  describe('DEmpn Management Detail Component', () => {
    let comp: DEmpnDetailComponent;
    let fixture: ComponentFixture<DEmpnDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [DEmpnDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ dEmpn: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(DEmpnDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DEmpnDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load dEmpn on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dEmpn).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
