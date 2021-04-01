import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AdhocDetailComponent } from './adhoc-detail.component';

describe('Component Tests', () => {
  describe('Adhoc Management Detail Component', () => {
    let comp: AdhocDetailComponent;
    let fixture: ComponentFixture<AdhocDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [AdhocDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ adhoc: { id: 'ABC' } }) },
          },
        ],
      })
        .overrideTemplate(AdhocDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AdhocDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load adhoc on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.adhoc).toEqual(jasmine.objectContaining({ id: 'ABC' }));
      });
    });
  });
});
