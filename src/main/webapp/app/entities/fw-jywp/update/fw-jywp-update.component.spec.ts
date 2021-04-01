jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { FwJywpService } from '../service/fw-jywp.service';
import { IFwJywp, FwJywp } from '../fw-jywp.model';

import { FwJywpUpdateComponent } from './fw-jywp-update.component';

describe('Component Tests', () => {
  describe('FwJywp Management Update Component', () => {
    let comp: FwJywpUpdateComponent;
    let fixture: ComponentFixture<FwJywpUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let fwJywpService: FwJywpService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [FwJywpUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(FwJywpUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FwJywpUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      fwJywpService = TestBed.inject(FwJywpService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const fwJywp: IFwJywp = { id: 456 };

        activatedRoute.data = of({ fwJywp });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(fwJywp));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const fwJywp = { id: 123 };
        spyOn(fwJywpService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ fwJywp });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: fwJywp }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(fwJywpService.update).toHaveBeenCalledWith(fwJywp);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const fwJywp = new FwJywp();
        spyOn(fwJywpService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ fwJywp });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: fwJywp }));
        saveSubject.complete();

        // THEN
        expect(fwJywpService.create).toHaveBeenCalledWith(fwJywp);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const fwJywp = { id: 123 };
        spyOn(fwJywpService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ fwJywp });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(fwJywpService.update).toHaveBeenCalledWith(fwJywp);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
